(ns tz-converter.styles
  (:require
   [spade.core   :refer [defglobal defclass]]))

(defglobal defaults
  [:html {:height "100%"}
   [:body
    {:background-size "cover"
     :background-repeat :no-repeat
     :background-image "url(gradient.svg)"
     :font-family "Jost, sans-serif"
     :font-optical-sizing :auto
     :font-style :normal
     :margin "0"
     :height "100%"}
    [:div.ant-select
     {:width "100%"}]]])

(defclass level1
  []
  {:color :white})
