(ns tz-converter.events
  (:require
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [re-frame.core :as re-frame]
   [tz-converter.db :as db]
   [tz-converter.util :as util]
   ["@js-joda/timezone"]
   [tick.core :as t]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(defn- set-target-time [db]
  (let [{:keys [source-panel]} db
        {source-date-time :date-time
         source-timezone :timezone} (get db source-panel)
        target-panel (util/get-opposite-panel source-panel)
        {target-timezone :timezone} (get db target-panel)]
    (cond-> db
      (every? some? [source-date-time source-timezone target-timezone])
      (assoc-in [target-panel :date-time]
                (-> source-date-time
                    (t/in target-timezone)
                    t/date-time
                    t/instant) ))))

(re-frame/reg-event-db
 ::set-date-time
 (fn [db [_ panel-id time]]
   (-> db
       (assoc-in [panel-id :date-time] time)
       set-target-time)))

(re-frame/reg-event-db
 ::set-timezone
 (fn [db [_ panel-id timezone]]
   (-> db
       (assoc-in [panel-id :timezone] timezone)
       set-target-time)))

(re-frame/reg-event-db
 ::flip-source-panel
 (fn [db [_ ]]
   (-> db
       (update :source-panel {:left-panel :right-panel
                              :right-panel :left-panel})
       set-target-time)))
