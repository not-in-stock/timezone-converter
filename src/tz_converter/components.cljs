(ns tz-converter.components
  (:require
   [tz-converter.util :as util]
   [medley.core :as medley]
   [tick.core :as t]
   [clojure.string :as str]
   ["antd" :refer [TimePicker]]))

(defn- wrap-on-change [on-chnage]
  (fn [date time-str]
    (on-chnage (some-> date .toDate t/instant)
               (when-not (str/blank? time-str)
                 time-str))))

(defn- wrap-value [value]
  (some-> value t/inst util/->dayjs))

(defn time-picker [props]
  [:> TimePicker
   (-> props
       (medley/update-existing :value wrap-value)
       (medley/update-existing :on-change wrap-on-change))])
