(ns tz-converter.subs
  (:require
   [tz-converter.util :as util]
   [re-frame.core :as re-frame]
   [dayjs]))

(def timezones
  (util/get-available-timezones))

(re-frame/reg-sub
 ::default-timezone
 (fn [_ _]
   (util/get-current-timezone)))

(def now
  dayjs)

(re-frame/reg-sub
 ::default-time
 (fn [_ _]
   (now)))

(re-frame/reg-sub
 ::primary-panel
 (fn [{:keys [primary-panel]}]
   primary-panel))
