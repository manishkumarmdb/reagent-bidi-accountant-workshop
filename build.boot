(set-env!
 :source-paths    #{"src/cljs"}
 :resource-paths  #{"resources/public"}
 :dependencies '[[org.clojure/clojure "1.8.0" :scope "test"]
                 [boot/core "2.5.1"]
                 [adzerk/boot-cljs          "1.7.228-1"   :scope "test"]
                 [adzerk/boot-cljs-repl     "0.3.0"      :scope "test"]
                 [com.cemerick/piggieback "0.2.1"  :scope "test"]
                 [weasel                  "0.7.0"  :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]
                 [adzerk/boot-reload        "0.4.7"      :scope "test"]
                 [pandeiro/boot-http        "0.7.3"      :scope "test"]
                 [crisptrutski/boot-cljs-test "0.2.2-SNAPSHOT" :scope "test"]
                 [org.clojure/clojurescript "1.8.40" :scope "test"]
                 [ring "1.4.0" :scope "test"]
                 [reagent "0.6.0-alpha" :scope "test"]
                 [tolitius/boot-check "0.1.1" :scope "test"]
                 [reagent-utils "0.2.0" :scope "test"]
                 [bidi "2.0.9" :scope "test"]
                 [venantius/accountant "0.1.7" :scope "test"]])

(require
 '[adzerk.boot-cljs             :refer [cljs]]
 '[adzerk.boot-cljs-repl        :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload           :refer [reload]]
 '[pandeiro.boot-http           :refer [serve]]
 '[crisptrutski.boot-cljs-test  :refer [test-cljs]]
 '[tolitius.boot-check :as check])

;; Static code analysis
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftask lint []
  (set-env! :source-paths #{"src/cljs" "test/cljs"})
  (comp
   (check/with-bikeshed)
   (check/with-yagni)
   (check/with-kibit)))

;; Development mode
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftask run []
  (comp
   (serve)
   (watch)
   (cljs-repl)
   (reload)
   (cljs)))

(deftask development []
  (task-options! cljs
                 {:optimizations :none
                  :source-map true
                  :compiler-options {:asset-path "js/main.out"}}
                 reload {:on-jsload 'cljs-boot-starter.client/init})
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp
   (development)
   (run)
   (target :dir #{"target/dev"})))

;; Production mode
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftask production []
  (task-options! cljs {:optimizations :advanced})
  identity)

(deftask prod
  "Simple alias to run application in production mode"
  []
  (comp
   (production)
   (cljs)
   (target :dir #{"target/prod"})))


;; Testing mode
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftask testing []
  (set-env! :source-paths #(conj % "test/cljs"))
  identity)

;;; This prevents a name collision WARNING between the test task and
;;; clojure.core/test, a function that nobody really uses or cares
;;; about.
(ns-unmap 'boot.user 'test)

(deftask test-once []
  (comp (testing)
        (test-cljs :js-env :phantom
                   :exit?  true)))

(deftask test []
  (comp (testing)
        (watch)
        (test-cljs :js-env :phantom)))
