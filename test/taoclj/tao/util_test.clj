(ns taoclj.tao.util-test
  (:require [clojure.test :refer :all]
            [taoclj.tao.util :as util]))


(deftest in?-finds-matches
  (are [sequence element expected]
       (= expected (util/in? sequence element))
    [:a] :a true
    [:a :b] :b true
    [:a] :b nil))

(deftest any-matches?-finds-matches
  (are [sequence1 sequence2 expected]
       (= expected (util/any-matches? sequence1 sequence2))
    [:a] [:a] true
    [:a :b] [:b] true
    [:a] [:b] nil))


(deftest all-matches-finds-matches
  (are [sequence1 sequence2 expected]
       (= expected (util/all-matches sequence1 sequence2))
       [:a] [:b] '()
       [:a] [:a] '(:a)
       [:a :b :c] [:a :c :d] '(:a :c)))
