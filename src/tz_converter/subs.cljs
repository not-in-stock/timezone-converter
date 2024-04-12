(ns tz-converter.subs
  (:require
   [re-frame.core :as re-frame]))

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

(re-frame/reg-sub
 ::get-date
 (fn [db [_ panel-id]]
   (-> db panel-id :time)))
