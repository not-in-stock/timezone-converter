(ns tz-converter.util
  (:require [re-frame.core :as re-frame])
  (:require-macros [tz-converter.util]))

(defn get-current-timezone []
  (-> js/Intl
      .DateTimeFormat
      .resolvedOptions
      .-timeZone))

(def <sub (comp deref re-frame/subscribe))
(def >evt re-frame/dispatch)
