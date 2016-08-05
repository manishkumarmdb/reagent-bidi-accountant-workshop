(ns cljs-boot-starter.signup
  (:require [cljs-boot-starter.components :as comp]))

(defn sign-up []
  [:div
   [:form
    [:div
     [:label " First Name : "]
     [comp/input {:type "text"
                  :placeholder "enter your first name"
                  :name "firstname"}]]
    [:div
     [:label " Last Name : "]
     [comp/input {:type "text"
                  :placeholder "enter your last name"
                  :name "lastname"}]]
    [:div
     [:label " Email : "]
     [comp/input {:type "text"
                  :placeholder "enter valid email id"
                  :name "emailid"}]]
    [:div
     [:label " Password : "]
     [comp/input {:type "password"
                  :placeholder "enter any password"
                  :name "password"}]]
    [:div
     [:label " Re-enter Password : "]
     [comp/input {:type "password"
                  :placeholder "re-enter above password"
                  :name "password"}]]
    [:div
     [:label " "]
     [comp/button {:type "submit"} "Submit"]]]])
