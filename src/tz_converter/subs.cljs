(ns tz-converter.subs
  (:require
   [re-frame.core :as re-frame]
   [tick.core :as t]))

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

(defn in-time-zone [date-time timezone]
  (-> date-time
      (t/in timezone)
      t/date-time
      t/instant))

(re-frame/reg-sub
 ::date-time
 (fn [db [_ panel-id]]
   (let [date-time (-> db panel-id :date-time)
         timezone (-> db panel-id :timezone)]
     (cond-> date-time
       (and (some? timezone) (some? date-time))
       (in-time-zone timezone)))))

(re-frame/reg-sub
 ::panel-disabled?
 :<- [::source-panel]
 (fn [source-panel [_ panel-id]]
   (not= panel-id source-panel)))
