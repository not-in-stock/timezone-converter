(ns tz-converter.db
  (:require [tz-converter.util :as util]))

(def default-db
  {:source-panel :left-panel
   :right-panel {:timezone "UTC"}
   :left-panel {:timezone (util/get-current-timezone)}})
