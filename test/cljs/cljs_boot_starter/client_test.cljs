(ns cljs-boot-starter.client-test
  (:require [cljs.test :refer-macros [deftest is]]))

(deftest is_1_0
  (is (zero? 1)))

(deftest is_1_1
  (is (= 1 1)))
