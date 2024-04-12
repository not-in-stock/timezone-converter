(ns tz-converter.views
  (:require
   [tz-converter.styles :as styles]
   [tz-converter.subs :as subs]
   [tz-converter.events :as events]
   [tz-converter.components :refer [time-picker]]
   [tz-converter.util :as util :refer [<sub >evt]]
   ["@ant-design/icons" :refer [ArrowRightOutlined
                                ArrowLeftOutlined]]
   [clojure.string :as str]
   [reagent.core :as r]
   [cljs-bean.core :refer [bean]]
   ["antd" :refer [Select
                   Typography
                   ConfigProvider
                   Button
                   Divider
                   theme]]))

(defn- zone-id->label [timezone]
  (str/replace timezone #"_" " "))

(defn- create-group-options [zones]
  (->> zones
       (sort-by :timezone)
       (map (fn [{:keys [timezone raw-timezone]}]
              {:label (zone-id->label timezone)
               :value raw-timezone}))))

(def time-zone-grouped-options
  (->> util/timezones
       (map (fn [tz]
              (if (str/includes? tz "/")
                (let [[group timezone] (str/split tz #"/" 2)]
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
   [time-picker
    {:format timepicker-format
     :value (<sub [::subs/get-date :left-panel])
     :disabled (not= :left-panel (<sub [::subs/source-panel]))
     :on-change (fn [date-time _]
                  (>evt [::events/set-date-time :left-panel date-time]))}]])

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
     :disabled (not= :right-panel (<sub [::subs/source-panel]))
     :value (<sub [::subs/get-date :right-panel])
     :on-change (fn [date-time _]
                  (>evt [::events/set-date-time :right-panel date-time]))}]])

(defn direction-button []
  (let [source-panel (<sub [::subs/source-panel])]
    [:div (styles/button-container)
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
