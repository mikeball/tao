(ns tao.core-test
  (:use clojure.test
        tao.core))




(deftest wrap-user-handles-nil
  (= (-> (wrap-user {} nil) :tao :authenticate) nil))

(deftest wrap-user-looks-up-user
  (= (-> (wrap-user {} (fn [ctx] (str "user:" "bob")))
         :tao
         :user)
     "user:bob"))




;; authenticate fn is ignored if nil