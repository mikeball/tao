(ns taoclj.tao-test
  (:require [clojure.test :refer :all]
            [taoclj.tao :as tao]))




;; need to build out!
(deftest wrap-auth-handles-nil
  (= (-> (wrap-auth {} nil) :tao :authenticate) nil))

(deftest wrap-auth-looks-up-user
  (= (-> (wrap-auth {} (fn [ctx] (str "user:" "bob")))
         :user)
     "user:bob"))









