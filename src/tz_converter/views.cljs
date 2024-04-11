(ns tz-converter.views
  (:require
   [re-frame.core :as re-frame]
   [tz-converter.styles :as styles]
   [tz-converter.subs :as subs]
   [tz-converter.util :as util :refer [<sub >evt]]
   [clojure.string :as str]
   [cljs-bean.core :refer [bean]]
   [tick.core :as t]
   ["antd" :refer [Select
                   TimePicker
                   Typography
                   ConfigProvider
                   Divider
                   theme]]))

(def timezones
  (util/generate-zone-ids))

(def default-timezone
  (let [current-timezone (util/get-current-timezone)]
    (when (contains? timezones current-timezone)
      current-timezone)))

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
                        :options (map (fn [{:keys [timezone raw-timezone]}]
                                        {:label (str/replace timezone #"_" " ")
                                         :value raw-timezone})
                                      zones)})) [])))

(defn filter-time-zone [term option]
  (let [{:keys [value]} (bean option)]
    (some-> value
            (str/replace #"_" " ")
            (str/includes? term))))

(def timepicker-format
  "HH:mm")


(prn (<sub [::subs/default-time]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div {:style {:backdrop-filter "blur(30px)"
                   :height "100%"}}
     [:div (styles/title-container)
      [:> Typography.Title {:level 3}
       "Timezone converter"]]
     [:div (styles/input-row)
      [:div (styles/input-column)
       [:> TimePicker {:format timepicker-format
                       :default-value (<sub [::subs/default-time])
                       :on-change (fn [x]
                                    (js/console.log x)) }]
       [:> Select {:filter-option filter-time-zone
                   :default-value (<sub [::subs/default-timezone])
                   ;; :on-change
                   :show-search true
                   :placeholder "Select Timezone"
                   :options time-zone-grouped-options}]]
      [:> Divider {:type "vertical"} ]
      [:div (styles/input-column)
       [:> TimePicker {:format timepicker-format}]
       [:> Select {:show-search true
                   :filter-option filter-time-zone
                   :placeholder "Select Timezone"
                   :options time-zone-grouped-options}]]]]))
