(ns tz-converter.util
  (:import (java.time ZoneId)))

(defmacro generate-zone-ids []
  (let [zone-ids# (set (ZoneId/getAvailableZoneIds))]
    zone-ids#))
