(ns tz-converter.views
  (:require
   [re-frame.core :as re-frame]
   [tz-converter.styles :as styles]
   [tz-converter.subs :as subs]
   [tz-converter.util :as util]
   [clojure.string :as str]
   [cljs-bean.core :refer [bean]]
   ["antd" :refer [Select TimePicker ConfigProvider Divider theme]]))

(def timezones
  (util/generate-zone-ids))

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

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div {:style {:backdrop-filter "blur(30px)"
                   :height "100%"}}
     [:> ConfigProvider {:theme {:algorithm (.-darkAlgorithm theme)}}]
     [:div {:class (styles/heading)}
      "Hello from " @name]
     [:div
      {:style {:display :flex
               :height "100%"
               :align-items :center
               :justify-content :center
               :gap "8px"}}
      [:div {:style {:width "200px"
                     :gap "16px"
                     :display :flex
                     :flex-direction :column}}
       [:> TimePicker {:format timepicker-format}]
       [:> Select {:style "200px"
                   :filter-option filter-time-zone
                   :show-search true
                   :placeholder "Select Timezone"
                   :options time-zone-grouped-options}]]
      [:> Divider {:type "vertical"} ]
      [:div {:style {:width "200px"
                     :gap "16px"
                     :display :flex
                     :flex-direction :column}}
       [:> TimePicker {:format timepicker-format}]
       [:> Select {:style "200px"
                   :show-search true
                   :filter-option filter-time-zone
                   :placeholder "Select Timezone"
                   :options time-zone-grouped-options}]]]]))
