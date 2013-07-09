(ns taoclj.tao.response-test
  (:require [clojure.test :refer :all]
            [taoclj.tao.response :as response]))



(deftest set-location-ignores-nil
  (is (= (response/set-location {} nil) {})))

(deftest set-location-sets-value
  (is (= (response/set-location {} "/login") {"Location" "/login"})))


(deftest set-cookies-key-not-set-if-nil
  (is (= {:a 1} (response/set-cookies {:a 1} nil))))

(deftest set-cookies-sets-value
  (is (= {:a 1 :cookies {:val "v"}} (response/set-cookies {:a 1} {:val "v"}))))


(deftest set-content-type-ignores-nil
  (is (= (response/set-content-type {} "text" nil) {"Content-Type" "text"})))

(deftest set-content-type-sets-value
  (is (= (response/set-content-type {} "text" "html") {"Content-Type" "html"})))



(deftest status-code-is-proxied
  (is (= 200 (:status (response/proxy-to-ring "" [200 {} ""])))))

(deftest body-is-proxied
  (is (= "hi" (:body (response/proxy-to-ring "" [200 {} "hi"])))))

(deftest default-content-type-is-proxied
  (is (= "text" (-> (response/proxy-to-ring "text" [200 {} ""])
                    (get :headers)
                    (get "Content-Type")))))

(deftest content-type-is-proxied
  (is (= "text" (-> (response/proxy-to-ring "" [200 {:content-type "text"} ""])
                    (get :headers)
                    (get "Content-Type")))))

(deftest location-is-proxied
  (is (= "/home" (-> (response/proxy-to-ring "" [200 {:location "/home"} ""])
                    (get :headers)
                    (get "Location")))))

(deftest cookies-are-proxied
  (is (= "ticket" (:cookies (response/proxy-to-ring "" [200 {:cookies "ticket"} ""])))))
