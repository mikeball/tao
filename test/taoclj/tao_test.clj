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

(deftest generated-dispatch-returns-not-found-on-no-handler-match
  (is (= {:status 404
          :headers {"Content-Type" "ct"}
          :body "nf"}
         ((tao/gen-dispatch '(["/a" {}]) 
                            "ct" 
                            (fn [r] [404 {} "nf"])
                            (fn [r] [403 {} "na"]))
          {:uri "/b"
           :request-method :get}))))


(deftest generated-dispatch-enforces-authorization
  (is (= {:status 403
          :headers {"Content-Type" "ct"}
          :body "na"}
         ((tao/gen-dispatch '(["/a" {:get ('h :b)}]) 
                            "ct"
                            (fn [r] [404 {} "nf"])
                            (fn [r] [403 {} "na"]))
          {:uri "/a"
           :request-method :get
           :user nil}))))

(deftest generated-dispatch-allows-valid-roles
  (is (= {:status 200
          :headers {"Content-Type" "ct"}
          :body "ok"}
         ((tao/gen-dispatch [["/a" {:get [(fn [r] [200 {} "ok"]) :a]}]] 
                            "ct"
                            (fn [r] [404 {} "nf"])
                            (fn [r] [403 {} "na"]))
          {:uri "/a"
           :request-method :get
           :user {:roles [:a]}}))))



(deftest path-parameters-are-passed-to-handlers
  (is (= "1"
         (:body ((tao/gen-dispatch [["/a/:id" {:get [(fn [r] [200 {} (-> r :params :id)]) :public]}]] 
                                   "ct"
                                   (fn [r] [404 {} "nf"])
                                   (fn [r] [403 {} "na"]))
                 {:uri "/a/1"
                  :request-method :get})))))
