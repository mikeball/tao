(ns taoclj.tao
  (:require [taoclj.tao.routing :as routing]
            [taoclj.tao.response :as response]))


(defn wrap-auth
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
      (let [request-roles []
            match (routing/match routes not-found not-authorized
                                 (request :uri) (request :request-method) request-roles)]
    
        ;; invoke the handler and convert back to ring map
        (response/proxy-to-ring
         ((:handler match) request)))))


(defmacro defroutes [name & route-data]
  `(def ~name (routing/build ~@route-data)))




