(ns tz-converter.styles
  (:require
   [spade.core   :refer [defglobal defclass]]))

(defglobal defaults
  [:html {:height "100%"}
   [:body
    {:background-size "cover"
     :background-repeat :no-repeat
     :font-family "Jost, sans-serif"
     :font-optical-sizing :auto
     :font-style :normal
     :margin "0"
     :height "100%"}
    [:div#app
     {:height "100%"}]
    [:div.ant-select
     {:width "100%"}]
    [:div.ant-picker
     {:width "100%"}]
    [:div.ant-divider
     {:height "100%"}]]])

(defclass heading
  []
  {:color :white})
