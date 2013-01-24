(ns taoclj.tao-tests
  (:use clojure.test taoclj.tao))



(deftest wrap-user-handles-nil
  (= (-> (wrap-user {} nil) :tao :authenticate) nil))

(deftest wrap-user-looks-up-user
  (= (-> (wrap-user {} (fn [ctx] (str "user:" "bob")))
         :user)
     "user:bob"))




;; authenticate fn is ignored if nil