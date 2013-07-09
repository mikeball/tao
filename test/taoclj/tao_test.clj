(ns taoclj.tao-test
  (:require [clojure.test :refer :all]
            [taoclj.tao :as tao]))


(deftest wrap-user-returns-wrapped-handler-call
  (is (= {:b 2} ((tao/wrap-user (fn [r] {:b 2}) nil) {:a 1}))))

(deftest wrap-user-adds-user-to-request
  (is (= {:a 1 :user nil} ((tao/wrap-user (fn [r] r) 'authenticate) {:a 1}))))

(deftest wrap-user-attaches-authenticate-result
  (is (= {:a 1 :user {:name "bob"}} ((tao/wrap-user (fn [r] r)
                                                    (fn [r] {:name "bob"})) {:a 1}))))











