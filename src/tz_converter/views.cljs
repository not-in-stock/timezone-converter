(ns tz-converter.views
  (:require
   [re-frame.core :as re-frame]
   [tz-converter.styles :as styles]
   [tz-converter.subs :as subs]
   [tz-converter.util :as util]))

(def timezones
  (util/generate-zone-ids))

(defn- select [props]
  (let [{:keys [options]} props]
    [:select (dissoc props :options)
     (doall
      (for [option options
            :let [{:keys [value label]} option]]
        ^{:key value}
        [:option {:value value}
         label]))]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:div
      {:class (styles/level1)}
      "Hello from " @name]
     [:div
      [:input]
      [:select]]
     [:div
      [:input]
      [:select]]]))
