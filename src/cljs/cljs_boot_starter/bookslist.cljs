(ns cljs-boot-starter.bookslist)

(defonce author-and-books
  [{:author "Thomas H. Cormen" :books ["Introduction to Algorithms" "Algorithms Unlocked"]}
   {:author "Narasimha Karumanchi" :books ["Data Structures and Algorithms Made Easy"]}
   {:author "Robert Sedgewick" :books ["Algorithms, 4th edition" "An Introduction to Programming in Java" "An Introduction to Computer Science"]}]
  )

(defn book-list []
  [:div
   [:table.table
    [:thead>tr
     [:th "Author's Name"]
     [:th "Book's Name"]]
    #_[:tbody
       [:tr
        [:td "1"]
        [:td "2"]]
       [:tr.tr
        [:td "3"]
        [:td "4"]]
       [:tr
        [:td "5"]
        [:td "6"]]]
    [:tbody
     (for [v author-and-books]
       ^{:key (:author v)}
       [:tr
        [:td
         (:author v)]
        [:td
         (get (:books v) 0)]])]
    ]])
