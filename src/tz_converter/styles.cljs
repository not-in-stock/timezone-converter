(ns tz-converter.styles
  (:require
   [spade.core   :refer [defglobal defclass]]))

(defglobal defaults
  [:body
   {:background-size "cover"
    :background-repeat :no-repeat
    :font-family "Jost, sans-serif"
    :font-optical-sizing :auto
    :font-style :normal}])

(defclass level1
  []
  {:color :white})
