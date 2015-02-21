(ns taoclj.tao.routing-test
  (:require [clojure.test :refer :all]
            [taoclj.tao.routing :as tao]))


(deftest ring-response-is-not-altered
  (is (= {:status 200} (tao/proxy-response {:status 200}))))


(deftest status-code-is-proxied
  (is (= 200 (:status (tao/proxy-response [200 {} ""])))))


(deftest body-is-proxied
  (is (= "hi" (:body (tao/proxy-response [200 {} "hi"])))))


(deftest content-type-is-proxied
  (is (= "text" (-> (tao/proxy-response [200 {:content-type "text"} ""])
                    (get :headers)
                    (get "Content-Type")))))


(deftest location-is-proxied
  (is (= "/home" (-> (tao/proxy-response [200 {:location "/home"} ""])
                     (get :headers)
                     (get "Location")))))


(deftest cookie-is-proxied-to-cookies
  (is (= {:status 200 :body "" :cookies {"name" {:value "val"}}}
         (tao/proxy-response [200 {:cookie {"name" {:value "val"}}} ""]))))


(deftest content-type-setting-is-required
  (is (thrown? IllegalArgumentException (tao/fn-dispatch {:routes '(["/" {}])}))))


(deftest generated-dispatch-sets-default-content-type
   (is (= {:status 200 :headers {"Content-Type" "ct"} :body "hi"}
          ((tao/fn-dispatch {:routes         [["/a" {:get [(fn [_] [200 {} "hi"]) :public]}]]
                             :content-type   "ct"})
           {:uri "/a" :request-method :get}))))




(deftest roles-are-enforced
   (is (= {:status 403 :headers {"Content-Type" "ct"} :body "not authorized"}

          ((tao/fn-dispatch {:routes        [["/a" {:get [(fn [_] [200 {} "hi"]) :myrole]}]]
                             :content-type  "ct"
                             :not-authorized (fn [r] [403 {} "not authorized"]) })
           {:uri "/a" :request-method :get}))))



(deftest roles-are-allowed
   (is (= {:status 200 :headers {"Content-Type" "ct"} :body "hi"}

          ((tao/fn-dispatch {:routes         [["/a" {:get [(fn [_] [200 {} "hi"]) :myrole]}]]
                             :content-type   "ct"
                             :authenticate   (fn [r] {:roles [:myrole]})}
                            )
           {:uri "/a" :request-method :get}))))






; (clojure.test/run-tests 'taoclj.tao-test)







