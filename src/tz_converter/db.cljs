(ns tz-converter.db
  (:require [tz-converter.util :as util]
            [tick.core :as t]))

(def default-db
  {:source-panel :left-panel
   :right-panel {:timezone "UTC"}
   :left-panel {:date-time (t/now)
                :timezone (util/get-current-timezone)}})
