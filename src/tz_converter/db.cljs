(ns tz-converter.db
  (:require [tz-converter.util :as util]
            [tick.core :as t]))

(def default-db
  {:source-panel :left-panel
   :date-time (t/now)
   :right-panel {:timezone "UTC"}
   :left-panel {:timezone (util/get-current-timezone)}})
