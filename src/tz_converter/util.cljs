(ns tz-converter.util
  (:require [re-frame.core :as re-frame]))

(defn get-current-timezone []
  (-> js/Intl
      .DateTimeFormat
      .resolvedOptions
      .-timeZone))

(defn get-available-timezones []
  (set (js/Intl.supportedValuesOf "timeZone")))

(def <sub (comp deref re-frame/subscribe))

(def >evt re-frame/dispatch)
