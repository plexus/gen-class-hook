(ns my.hooks
  (:require [clojure.java.io :as io]))

(defn pre-load-hook [test-plan]
  ;; compiles to *compile-path*, which defaults to "classes". Clojure will be
  ;; able to import the class after this *if* classes is on the classpath (in
  ;; deps.edn :paths) *and* the directory existed when clojure booted.
  (compile 'my.klass)
  test-plan)
