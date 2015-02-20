(ns taoclj.tao-test
  (:require [clojure.test :refer :all]
            [taoclj.tao :as tao]))



(deftest short-condition-returns
  (is (= nil
         (tao/short-> {:person {:name "bob"}}
                      (:personx)
                      (:name)

             :on nil? ))))


(deftest short-condition-passes
  (is (= "bob"
         (tao/short-> {:person {:name "bob"}}
                      (:person)
                      (:name)

             :on nil? ))))



(deftest rsx-returns-first-response
  (is (= [200 "bill is not allowed"]

         (tao/rsx-> {:name "bill"}

                    ((fn [user]
                       (if (= "bill" (:name user))
                          [200 "bill is not allowed"]
                          user)))

                    (:name)))))


(deftest rsx-returns-fully-threaded-result
  (is (= "bob"
         (tao/rsx-> {:name "bob"}

                    ((fn [user]
                       (if (= "bill" (:name user))
                          [200 "bill is not allowed"]
                          user)))

                    (:name)))))
