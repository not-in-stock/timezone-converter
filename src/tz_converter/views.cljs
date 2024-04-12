(ns tz-converter.views
  (:require
   [tz-converter.styles :as styles]
   [tz-converter.subs :as subs]
   [tz-converter.events :as events]
   [tz-converter.util :as util :refer [<sub >evt]]
   [clojure.string :as str]
   [cljs-bean.core :refer [bean]]
   ["antd" :refer [Select
                   TimePicker
                   Typography
                   ConfigProvider
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

(defn left-panel-pickers []
  [:div#left-panel (styles/input-column)
   [:> Select {:show-search true
               :filter-option filter-time-zone
               :allow-clear true
               :default-value (<sub [::subs/default-timezone])
               :on-change #(>evt [::events/set-timezone :left-panel %])
               :on-clear #(>evt [::events/set-timezone :left-panel nil])
               :placeholder "Select Timezone"
               :options time-zone-grouped-options}]
   [:> TimePicker
    {:format timepicker-format
     :default-value (<sub [::subs/default-time])
     :on-change (fn [_ time]
                  (>evt [::events/set-time :left-panel
                         (when-not (str/blank? time)
                           time)]))}]])

(defn right-panle-pickers []
  [:div#right-panle (styles/input-column)
   [:> Select {:show-search true
               :allow-clear true
               :filter-option filter-time-zone
               :placeholder "Select Timezone"
               :on-change #(>evt [::events/set-timezone :right-panel %])
               :on-clear #(>evt [::events/set-timezone :right-panel nil])
               :options time-zone-grouped-options}]
   [:> TimePicker
    {:format timepicker-format
     :on-change (fn [_ time]
                  (>evt [::events/set-time :right-panel
                         (when-not (str/blank? time)
                           time)]))}]])

(defn direction-marker []
  [:div (styles/title-container)
   [:> Typography.Title {:level 5}
    (if (= :left-panel (<sub [::subs/primary-panel]))
      "->"
      "<-")]])

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
     [direction-marker]
     [:> Divider {:type "vertical"}]]
    [right-panle-pickers]]])
