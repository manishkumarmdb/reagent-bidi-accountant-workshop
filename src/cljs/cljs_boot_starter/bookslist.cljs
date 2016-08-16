(ns cljs-boot-starter.bookslist)

(defonce author-and-books
  [{:author "Thomas H. Cormen" :books ["Introduction to Algorithms"
                                       "Algorithms Unlocked"]}
   {:author "Narasimha Karumanchi" :books ["Data Structures and Algorithms Made Easy"]}
   {:author "Robert Sedgewick" :books ["Algorithms, 4th edition"
                                       "An Introduction to Programming in Java"
                                       "An Introduction to Computer Science"]}]
  )

(defn book-list []
  [:div
   [:table.books-table
    [:thead>tr
     [:th.books-table-th "Author's Name"]
     [:th.books-table-th "Book's Name"]]
    [:tbody
     (for [v author-and-books]
       ^{:key (:author v)}
       [:tr
        [:td.books-table-td
         (:author v)]
        [:td.books-table-td
         (for [b (range (count (:books v)))]
           ^{:key b}
           #_[:p
              (-> (:books v)
                  (get b))]
           [:table
            [:tbody
             [:tr
              [:td.books-list-table-td
               (-> (:books v)
                   (get b)
                   (str "."))]]]])]
        ])]
    ]])
