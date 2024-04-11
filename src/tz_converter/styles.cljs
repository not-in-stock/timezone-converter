(ns tz-converter.styles
  (:require
   [spade.core :refer [defglobal defclass defattrs]]))

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

(defattrs title-container []
  {:display :flex
   :justify-content :center})

(defattrs input-row []
  {:display :flex
   :height "100%"
   :align-items :center
   :justify-content :center
   :gap "8px"})

(defattrs input-column []
  {:width "200px"
   :gap "16px"
   :display :flex
   :flex-direction :column})
