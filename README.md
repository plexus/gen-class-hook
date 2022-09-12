Example of using Kaocha when you have a :gen-class.

Uses a hook to compile the class so it can be loaded by the test.

You need these things:

Make sure "classes" is on the classpath

```clj
;; deps.edn
{:paths ["src" "classes"]}
```

Make sure it exists before Clojure starts

```
mkdir classes
```

Add a Kaocha hook to compile the classes

```clj
;; tests.edn
#kaocha/v1
{:plugins [:hooks]
 :kaocha.hooks/pre-load [my.hooks/pre-load-hook]}
```

```clj
(ns my.hooks
  (:require [clojure.java.io :as io]))

(defn pre-load-hook [test-plan]
  ;; compiles to *compile-path*, which defaults to "classes". Clojure will be
  ;; able to import the class after this *if* classes is on the classpath (in
  ;; deps.edn :paths) *and* the directory existed when clojure booted.
  (compile 'my.klass)
  test-plan)
```

What's left is adding the implementation

```clj
(ns my.klass)

(gen-class :name my.cool.Klass)
```

And a test

```clj
(ns my.klass-test
  (:require [clojure.test :refer :all])
  (:import my.cool.Klass))

(deftest klass-test
  (is (instance? Klass (Klass.))))
```

Now you can `bin/kaocha` and the test will work.

Note that the JVM is not particularly good at **reloading** classes, so a
`bin/kaocha --watch` will not work as expected. But you can have your class call
clojure functions, and redefine those.
