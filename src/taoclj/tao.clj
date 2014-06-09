(ns taoclj.tao
  (:require [taoclj.via :as via]
            [potemkin]))


; bring in deftable macro for easy reference by users
(potemkin/import-vars
 [taoclj.via deftable])




(defn- set-status [ring tao]
  (assoc ring :status (first tao)))


(defn- set-body [ring tao]
   (let [body (nth tao 2)]
     (if-not body ring
       (assoc ring :body body))))


(defn- set-content-type [ring tao]
   (let [ct (:content-type (second tao))]
     (if-not ct ring
       (assoc-in ring [:headers "Content-Type"] ct))))


(defn- set-location [ring tao]
   (let [loc (:location (second tao))]
     (if-not loc ring
       (assoc-in ring [:headers "Location"] loc))))


(defn- set-cookies [ring tao]
   (let [cook (:cookie (second tao))]
     (if-not cook ring
       (assoc ring :cookies cook))))



(defn proxy-response "Converts tao response to ring response"
  [response]

  (if (map? response) response ; this is a regular ring response

    ; this is a tao response
    (-> (set-status {} response)
        (set-body response)
        (set-content-type response)
        (set-location response)
        (set-cookies response))))



(defn validate [settings]
  (cond (not (string? (:content-type settings)))
        (throw (IllegalArgumentException.
                ":content-type setting is required and must be valid http content-type string"))))


{:headers {"Content-Type" "text/plain"}}

; set default content type if not set in tao response
(defn- set-default-content-type [ring default]
  (if (get-in ring [:headers "Content-Type"]) ring
    (assoc-in ring [:headers "Content-Type"] default)))


(set-default-content-type
 {:headers {"Content-Type" "ct1"}}
 "ct-default")

(set-default-content-type {} "ct")



(defn fn-dispatch
  "Build a handler function and enforces role restrictions

  (fn-dispatch {:routes my-routes
                :content-type \"text/html;charset=utf-8\"})
  "
  [settings]

  (validate settings)

  (let [default-ct   (:content-type settings)
        via-dispatch (via/fn-dispatch settings)]

    (fn [request]
      (-> request
          (via-dispatch)
          (proxy-response)
          (set-default-content-type default-ct)))))




; (clojure.test/run-tests 'taoclj.tao-test)








