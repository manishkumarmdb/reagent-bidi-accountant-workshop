(ns cljs-boot-starter.signup
  (:require [cljs-boot-starter.components :as comp]))

(defn sign-up []
  [:div
   [:form
    [:table.signup-table
     [:tbody
      [:tr
       [:td " First Name : "]
       [:td
        [comp/input {:type "text"
                     :placeholder "enter your first name"
                     :name "firstname"}]]]
      [:tr
       [:td " Last Name : "]
       [:td
        [comp/input {:type "text"
                     :placeholder "enter your last name"
                     :name "lastname"}]]]
      [:tr
       [:td " Email : "]
       [:td
        [comp/input {:type "text"
                     :placeholder "enter valid email id"
                     :name "emailid"}]]]
      [:tr
       [:td " Password : "]
       [:td
        [comp/input {:type "password"
                     :placeholder "enter any password"
                     :name "password"}]]]
      [:tr
       [:td " Re-enter Password : "]
       [:td
        [comp/input {:type "password"
                     :placeholder "re-enter above password"
                     :name "password"}]]]
      [:tr
       [:td ""]
       [:td
        [comp/input {:type "submit"} "Submit"]]]]]]])
