(ns tz-converter.subs
  (:require
   [tz-converter.util :as util]
   [re-frame.core :as re-frame]
   [dayjs]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(def timezones
  (util/generate-zone-ids))

(re-frame/reg-sub
 ::default-timezone
 (fn [_ _]
   (let [current-timezone (util/get-current-timezone)]
     (when (contains? timezones current-timezone)
       current-timezone))))

(def now
  "API reference https://day.js.org/docs/en/parse/now"
  dayjs)

(re-frame/reg-sub
 ::default-time
 (fn [_ _]
   (now)))
