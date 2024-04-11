(ns tz-converter.views
  (:require
   [re-frame.core :as re-frame]
   [tz-converter.styles :as styles]
   [tz-converter.subs :as subs]
   [tz-converter.events :as events]
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

(defn left-panel-pickers []
  [:div#left (styles/input-column)
   [:> TimePicker
    {:format timepicker-format
     :default-value (<sub [::subs/default-time])
     :on-change (fn [_ time]
                  (>evt [::events/set-time :left-panel time]))}]
   [:> Select {:filter-option filter-time-zone
               :default-value (<sub [::subs/default-timezone])
               :on-change #(>evt [::events/set-timezone :left-panel %])
               :show-search true
               :placeholder "Select Timezone"
               :options time-zone-grouped-options}]])

(defn right-panle-pickers []
  [:div#right (styles/input-column)
   [:> TimePicker
    {:format timepicker-format
     :on-change (fn [_ time]
                  (>evt [::events/set-time :right-panel time]))}]
   [:> Select {:show-search true
               :filter-option filter-time-zone
               :placeholder "Select Timezone"
               :on-change #(>evt [::events/set-timezone :right-panel %])
               :options time-zone-grouped-options}]])

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
