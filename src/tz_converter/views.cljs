(ns tz-converter.views
  (:require
   [tz-converter.styles :as styles]
   [tz-converter.subs :as subs]
   [tz-converter.events :as events]
   [tz-converter.util :as util :refer [<sub >evt]]
   [medley.core :as medley]
   ["@ant-design/icons" :refer [ArrowRightOutlined
                                ArrowLeftOutlined]]
   [tick.core :as t]
   [clojure.string :as str]
   [reagent.core :as r]
   [cljs-bean.core :refer [bean]]
   ["antd" :refer [Select
                   TimePicker
                   Typography
                   ConfigProvider
                   Button
                   Divider
                   theme]]))

(def timezones
  (util/get-available-timezones))

(defn- zone-id->label [timezone]
  (str/replace timezone #"_" " "))

(defn- create-group-options [zones]
  (->> zones
       (sort-by :timezone)
       (map (fn [{:keys [timezone raw-timezone]}]
              {:label (zone-id->label timezone)
               :value raw-timezone}))))

(def time-zone-grouped-options
  (->> timezones
       (map (fn [tz]
              (if (str/includes? tz "/")
                (let [[group timezone] (str/split tz #"/")]
                  {:raw-timezone tz
                   :group group
                   :timezone timezone})
                {:raw-timezone tz
                 :group "Other"
                 :timezone tz})))
       (group-by :group)
       (reduce (fn [option-groups [group zones]]
                 (conj option-groups
                       {:title group
                        :label group
                        :options (create-group-options zones)})) [])))

(defn filter-time-zone [term option]
  (let [{:keys [value]} (bean option)]
    (some-> value
            zone-id->label
            (str/lower-case)
            (str/includes? (str/lower-case term)))))

(def timepicker-format
  "HH:mm")

(defn- wrap-on-change [on-chnage]
  (fn [date time-str]
    (on-chnage (some-> date .toDate t/instant)
               (when-not (str/blank? time-str)
                 time-str))))

(defn time-picker [props]
  [:> TimePicker
   (-> props
       (medley/update-existing :on-change wrap-on-change))])

(defn left-panel-pickers []
  [:div#left-panel (styles/input-column)
   [:> Select {:show-search true
               :filter-option filter-time-zone
               :allow-clear true
               :value (<sub [::subs/left-timezone])
               :on-change #(>evt [::events/set-timezone :left-panel %])
               :on-clear #(>evt [::events/set-timezone :left-panel nil])
               :placeholder "Select Timezone"
               :options time-zone-grouped-options}]
   [:> TimePicker
    {:format timepicker-format
     :default-value (<sub [::subs/default-time])
     :on-change (fn [date-time _time]
                  (>evt [::events/set-time :left-panel date-time]))}]])

(defn right-panle-pickers []
  [:div#right-panle (styles/input-column)
   [:> Select {:show-search true
               :allow-clear true
               :filter-option filter-time-zone
               :placeholder "Select Timezone"
               :value (<sub [::subs/right-timezone])
               :on-change #(>evt [::events/set-timezone :right-panel %])
               :on-clear #(>evt [::events/set-timezone :right-panel nil])
               :options time-zone-grouped-options}]
   [time-picker
    {:format timepicker-format
     :on-change (fn [date-time _time]
                  (>evt [::events/set-time :right-panel date-time]))}]])

(defn direction-button []
  (let [source-panel (<sub [::subs/source-panel])]
    [:div (styles/title-container)
    [:> Button {:type "primary"
                :shape "circle"
                :on-click #(>evt [::events/flip-source-panel])
                :icon (r/as-element
                       (case source-panel
                         :left-panel [:> ArrowRightOutlined]
                         :right-panel [:> ArrowLeftOutlined]))}]]))

(defn main-panel []
  [:div {:style {:backdrop-filter "blur(30px)"
                 :height "100%"}}
   [:div (styles/title-container)
    [:> Typography.Title {:level 3}
     "Timezone converter"]]

   [:div (styles/input-row)
    [left-panel-pickers]
    [:div (styles/divider-column)
     [:> Divider {:type "vertical"}]
     [direction-button]
     [:> Divider {:type "vertical"}]]
    [right-panle-pickers]]])
