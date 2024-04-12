(ns tz-converter.styles
  (:require
   [garden.stylesheet :refer [at-media]]
   [spade.core :refer [defglobal defattrs]]))

(defglobal defaults
  [:html {:height "100%"}
   [:body
    {:background-size "cover"
     :background-repeat :no-repeat
     :font-family "Jost, sans-serif"
     :font-optical-sizing :auto
     :font-style :normal
     :margin "0"
     :height "100%"}]]
  [:div#app
   {:height "100%"}]
  [:div.ant-select
   {:width "100%"}]
  [:div.ant-picker
   {:width "100%"}]
  [:div.ant-divider
   {:height "100%"}])

(defattrs title-container []
  {:display :flex
   :justify-content :center})

(defattrs input-row []
  {:display :flex
   :height "100%"
   :align-items :center
   :justify-content :center
   :padding "16px"
   :gap "8px"}
  (at-media {:max-width "750px"}
   {:height :unset
    :padding "0 32px"
    :flex-direction :column}))

(defattrs input-column []
  {:width "200px"
   :gap "16px"
   :display :flex
   :flex-direction :column}
  (at-media {:max-width "750px"}
            {:width "100%"}))

(defattrs divider-column []
  {:display :flex
   :flex-direction :column
   :height "100%"
   :align-items :center})

(defattrs button-container []
  {:padding "16px 0"}
  (at-media {:max-width "750px"}
   {:padding "8px 0"
    :transform "rotate(90deg)"}))
