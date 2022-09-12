(ns my.klass-test
  (:require [clojure.test :refer :all])
  (:import my.cool.Klass))

(deftest klass-test
  (is (instance? Klass (Klass.))))
