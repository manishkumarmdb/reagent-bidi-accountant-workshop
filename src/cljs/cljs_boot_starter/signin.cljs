(ns cljs-boot-starter.signin
  (:require [cljs-boot-starter.components :as comp]))

(defn sign-in []
  [:div
   [:form
    [:table.signin-table
     #_[:thead>tr
        [:th.signin-th "username/email"]
        [:th.signin-th "password"]]
     [:tbody
      [:tr
       [:td
        [comp/input {:type "text"
                     :placeholder "enter your username/email"
                     :name "username"}]]
       [:td
        [comp/input {:type "password"
                     :placeholder "enter your password"
                     :name "password"}]]
       [:td
        [comp/input {:type "submit"} "Sign in"]]]]]]])
