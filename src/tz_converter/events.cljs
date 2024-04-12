(ns tz-converter.events
  (:require
   [re-frame.core :as re-frame]
   [tz-converter.db :as db]
   [tick.core :as t]
   ["@js-joda/timezone" :as js-joda-timezone]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(defn set-utc-datetime [db panel-id]
  (let [{:keys [time timezone]} (get db panel-id)]
    (cond-> db
      (and (some? time)
           (some? timezone))
      (assoc :utc {:time time
                   :timezone timezone}))))

(re-frame/reg-event-db
 ::set-time
 (fn [db [_ panel-id time]]
   (-> db
       (assoc-in [panel-id :time] time)
       (assoc :primary-panel panel-id)
       (set-utc-datetime panel-id))))

(re-frame/reg-event-db
 ::set-timezone
 (fn [db [_ panel-id timezone]]
   (-> db
       (assoc-in [panel-id :timezone] timezone)
       (assoc :primary-panel panel-id)
       (set-utc-datetime panel-id))))
