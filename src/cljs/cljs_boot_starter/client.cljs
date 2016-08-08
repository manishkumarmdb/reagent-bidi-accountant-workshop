(ns cljs-boot-starter.client
  (:require [clojure.string :as string]
            [reagent.core :as reagent :refer [atom render]]
            [reagent.session :as session]
            [bidi.bidi :as bidi]
            [accountant.core :as accountant]
            [cljs-boot-starter.signin :refer [sign-in]]
            [cljs-boot-starter.signup :refer [sign-up]]
            [cljs-boot-starter.bookslist :refer [book-list]]))

(enable-console-print!)

(defonce authors-name ["James Gosling" "Rich Hickey" "Bjarne Stroustrup" "Donald Knuth" "Robert Sedgewick"])

;; below authors-link according to above authors-name in sequence.
;; not connected link with authors-name
(defonce authors-wiki-link ["https://en.wikipedia.org/wiki/James_Gosling"
                            "https://en.wikipedia.org/wiki/Clojure"
                            "https://en.wikipedia.org/wiki/Bjarne_Stroustrup"
                            "https://en.wikipedia.org/wiki/Donald_Knuth"
                            "https://en.wikipedia.org/wiki/Robert_Sedgewick_(computer_scientist)"])

(def app-routes
  ["/"
   [["" :home]
    ["sign_in" :sign-in]
    ["sign_up" :sign-up]
    ["author"
     [["" :author-list]
      [["/name-" :author-id] :author-name]]]
    ["top-10-book" :top-10-books]
    ["about" :about]
    [true :page-not-define]
    ]])

(defmulti page-contents identity)

(defmethod page-contents :home []
  [:span
   [:ul
    [:li [:a {:href (bidi/path-for app-routes :sign-in) } "Sign-in"]]
    [:li [:a {:href (bidi/path-for app-routes :sign-up) } "Sign-up"]]
    [:li [:a {:href (bidi/path-for app-routes :author-list) } "Authors"]]
    [:li [:a {:href (bidi/path-for app-routes :top-10-books) } "Top 10 Books"]]
    [:li [:a {:href (bidi/path-for app-routes :about) } "About"]]
    ]])

(defmethod page-contents :sign-in []
  [:div
   [page-contents :home]
   [:hr]
   [:h3 "please sign-in here"]
   [sign-in]])

(defmethod page-contents :sign-up []
  [:div
   [page-contents :home]
   [:hr]
   [:h3 "Registration Form for new User."]
   [sign-up]])

(defmethod page-contents :author-list []
  [:div
   [page-contents :home]
   [:hr]
   [:h3 "Author's List :"]
   [:ul
    (map (fn [a-name]
           [:li {:key (str "name-" a-name)}
            [:a {:href (bidi/path-for app-routes
                                      :author-name
                                      :author-id (-> a-name
                                                     ;;here i'm removing whitespace character from author-name because in URI, whitespace is not allowed. for more details about please refer this link http://www.ietf.org/rfc/rfc3986.txt
                                                     ;;(string/split #"\s")
                                                     ;;(string/join)
                                                     ;;.toLowerCase
                                                     (string/replace #"\s" "_")
                                                     ))} a-name]])
         authors-name)]])

(defmethod page-contents :author-name []
  (let [routing-data (session/get :route)
        author-page (get-in routing-data [:route-params :author-id])]
    [:span
     [:h3 "You'r @ : " [:u
                        (-> author-page
                            (string/replace #"_" " "))] " : page"]
     [:p [:a {:href (bidi/path-for app-routes :author-list)} "Back to author list page"]]]))

(defmethod page-contents :top-10-books []
  [:span
   [page-contents :home]
   [:hr]
   [book-list]])

(defmethod page-contents :about []
  [:span
   [page-contents :home]
   [:hr]
   [:span [:h3 "About DSA Made Easy: written by Narasimha Karumanchi"]]
   [:p "The Data Structures and Algorithms Made Easy explains the
 data structure and algorithm concepts with source code.
 The user can understand concepts and test with C/C++ compiler.
 If the user wants to learn using java programming language, they use
 “Data Structures and Algorithms Made Easy in Java”.
 This book explains the concepts in 21 chapters which cover Recursion
 and Backtracking, Linked Lists, Stacks, Queues, Trees, Priority Queue
 and Heaps, String Algorithms, Algorithms Design Techniques, and more.
 It also explains with multiple approaches for each problem. The user
 can understand the algorithm analysis for each problem."]])

(defmethod page-contents :page-not-define []
  "non existing routes go here"
  [:span
   [:h2
    "Bidi Routing example: Test for this link is define on not ?"]
   [:hr]
   [:h3 "404: couldn't find this page"]
   [:pre.verse
    "what you are looking for, i don't have"]])

;; if i configure route and not defined i.e missing implementation.
(defmethod page-contents :default []
  "Configured routes, missing an implementation, go here"
  [:span
   [:h2 "404: page not found"]
   [:pre.verse
    "This page should be here, but I never created it."]])

(defn home []
  (fn []
    (let [page (:current-page (session/get :route))]
      [:div
       [:p [:a {:href (bidi/path-for app-routes :home) } "home"]]
       [:hr]
       (page-contents page)
       [:hr]
       [:p "***Routing Implementation using "
        [:a {:href "https://reagent-project.github.io/"} "Reagent"] ", "
        [:a {:href "https://github.com/juxt/bidi"} "Bidi"] " & "
        [:a {:href "https://github.com/venantius/accountant"} "Accountant"]]
       ])))

(defn init []
  (render [home] (.getElementById js/document "my-app-area")))

(defn ^:export init! []
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (let [match (bidi/match-route app-routes path)
            current-page (:handler match)
            route-params (:route-params match)]
        (session/put! :route {:current-page current-page
                              :route-params route-params})))
    :path-exists? (fn [path]
                    (boolean (bidi/match-route app-routes path)))})
  (accountant/dispatch-current!)
  (init))

(init!)
