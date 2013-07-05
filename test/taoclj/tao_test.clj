(ns taoclj.tao-test
  (:require [clojure.test :refer :all]
            [taoclj.tao :as tao]))




(deftest builder-attaches-roles-to-handlers
  ;; check to see if :
  
  )















;; need to build out!
(deftest wrap-user-handles-nil
  (= (-> (wrap-user {} nil) :tao :authenticate) nil))

(deftest wrap-user-looks-up-user
  (= (-> (wrap-user {} (fn [ctx] (str "user:" "bob")))
         :user)
     "user:bob"))




;; authenticate fn is ignored if nil





