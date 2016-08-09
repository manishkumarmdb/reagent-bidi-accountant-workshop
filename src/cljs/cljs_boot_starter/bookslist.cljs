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
   [:table
    [:thead>tr
     [:th "Author's Name"]
     [:th "Book's Name"]]
    [:tbody
     (for [v author-and-books]
       ^{:key (:author v)}
       [:tr
        [:td
         (:author v)]
        [:td
         (for [b (range (count (:books v)))]
           ^{:key b}
           [:li
            (-> (:books v)
                (get b))])]
        ])]
    ]])
