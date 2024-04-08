(ns tz-converter.util
  (:import (java.time ZoneId)))

(defmacro generate-zone-ids []
  (let [zone-ids# (vec (ZoneId/getAvailableZoneIds))]
    zone-ids#))
