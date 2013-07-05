(ns taoclj.tao.routing.path-test
  (:require [clojure.test :refer :all]
            [taoclj.tao.routing.path :as path]))



(deftest not-matched
  (nil? (path/match "/a" "/b")))

(deftest matched-returns-map-with-keywords-map
  (are [pattern path params] (= (path/match pattern path) params)
    "/:a"       "/a"       {:a "a"}
    "/:a/b/:c"  "/a/b/c"   {:a "a", :c "c"}))