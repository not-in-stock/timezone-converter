(ns tz-converter.events
  (:require
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [re-frame.core :as re-frame]
   [tz-converter.db :as db]
   ["@js-joda/timezone"]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::set-time
 (fn [db [_ panel-id time]]
   (-> db
       (assoc-in [panel-id :date-time] time))))

(re-frame/reg-event-db
 ::set-timezone
 (fn [db [_ panel-id timezone]]
   (-> db
       (assoc-in [panel-id :timezone] timezone))))

(re-frame/reg-event-db
 ::flip-source-panel
 (fn [db [_ ]]
   (update db :source-panel {:left-panel :right-panel
                             :right-panel :left-panel})))
