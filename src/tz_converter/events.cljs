(ns tz-converter.events
  (:require
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [re-frame.core :as re-frame]
   [tz-converter.db :as db]
   [tz-converter.util :as util]
   ["@js-joda/timezone"]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::set-date-time
 (fn [db [_ date-time]]
   (assoc db :date-time date-time)))

(re-frame/reg-event-db
 ::set-timezone
 (fn [db [_ panel-id timezone]]
   (assoc-in db [panel-id :timezone] timezone)))

(re-frame/reg-event-db
 ::flip-source-panel
 (fn [db [_ ]]
   (update db :source-panel util/get-opposite-panel)))
