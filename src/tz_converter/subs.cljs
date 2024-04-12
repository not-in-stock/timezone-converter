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
 ::source-panel
 (fn [{:keys [source-panel]}]
   source-panel))

(re-frame/reg-sub
 ::right-timezone
 (fn [db]
   (-> db :right-panel :timezone)))

(re-frame/reg-sub
 ::left-timezone
 (fn [db]
   (-> db :left-panel :timezone)))
