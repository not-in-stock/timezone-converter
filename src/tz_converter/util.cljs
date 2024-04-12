(ns tz-converter.util
  (:require [re-frame.core :as re-frame]
            ["@js-joda/timezone"]
            [cljc.java-time.zone-id :as zone-id]))

(defn get-current-timezone []
  (str (zone-id/system-default)))

(defn get-available-timezones []
  (set (zone-id/get-available-zone-ids)))

(def timezones
  (get-available-timezones))

(def <sub (comp deref re-frame/subscribe))

(def >evt re-frame/dispatch)
