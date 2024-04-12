(ns tz-converter.components
  (:require
   [tz-converter.util :as util]
   [medley.core :as medley]
   [tick.core :as t]
   [clojure.string :as str]
   [clojure.set :as set]
   ["antd" :refer [TimePicker]]))

(defn- wrap-on-change [on-chnage]
  (fn [date time-str]
    (on-chnage (some-> date .toDate t/instant)
               (when-not (str/blank? time-str)
                 time-str))))

(defn- wrap-value [value]
  (some-> value t/inst util/->dayjs))

(defn- transform-props [props]
  (cond-> props
    :always
    (medley/update-existing :value wrap-value)

    :always
    (medley/update-existing :on-change wrap-on-change)

    (contains? props :disabled?)
    (set/rename-keys {:disabled? :disabled})

    (:disabled-placeholder? props)
    (-> (dissoc :disabled-placeholder?)
        (assoc :placeholder nil))))

(defn time-picker [props]
  [:> TimePicker (transform-props props)])
