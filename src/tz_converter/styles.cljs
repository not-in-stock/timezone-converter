(ns tz-converter.styles
  (:require
   [garden.stylesheet :refer [at-media]]
   [spade.core :refer [defglobal defattrs]]))

(def mobile-screen {:max-width "750px"})

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

(defattrs app-background []
  {:display :flex
   :height "100%"
   :flex-direction :column})

(defattrs title-container []
  {:display :flex
   :justify-content :center})

(defattrs input-row []
  {:display :flex
   :flex-grow "1"
   :align-items :center
   :justify-content :center
   :padding "16px"
   :gap "8px"}
  (at-media mobile-screen
   {:height :unset
    :padding "0 32px"
    :flex-direction :column}))

(defattrs input-column []
  {:width "200px"
   :gap "16px"
   :display :flex
   :flex-direction :column}
  (at-media mobile-screen
            {:width "100%"}))

(defattrs divider-column []
  {:display :flex
   :flex-direction :column
   :height "100%"
   :align-items :center}
  (at-media mobile-screen
            {:height :unset}))

(defattrs button-container []
  {:padding "16px 0"}
  (at-media mobile-screen
   {:padding "8px 0"
    :transform "rotate(90deg)"}))
