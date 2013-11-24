(ns taoclj.tao
  (:require [taoclj.tao.routing :as routing]
            [taoclj.tao.request :refer [handler-method]]
            [taoclj.tao.response :as response]))


(defn wrap-user
  "Uses the authenticate handler to lookup and set the user making this request."
  [handler authenticate]
  (fn [request]
    (handler (assoc-in request [:user]
                       (cond (nil? authenticate) nil
                             :default (authenticate request))))))


(defn gen-dispatch
  "Build a tao handler function for your application"
  [routes content-type not-found not-authorized]
  (fn [request]
      (let [request-roles (-> request :user :roles)
            match (routing/match routes
                                 not-found
                                 not-authorized
                                 (request :uri)
                                 (handler-method request)
                                 request-roles)
            handler (match :handler)
            new-request (assoc request :params (merge (request :params)
                                                      (match :path-params)))]

        ;; invoke the handler and convert back to ring map
        (response/proxy-to-ring content-type
                                (handler new-request)))))


(defmacro deftable [name & route-data]
  `(def ~name (routing/build ~@route-data)))




