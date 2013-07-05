(ns taoclj.tao
  (:require [taoclj.tao.routing :as routing]
            [taoclj.tao.response :as response]))



;; perhaps rename wrap-auth or load-user
(defn wrap-user
  "Uses the authenticate handler to lookup and set the user making this request."
  [handler authenticate]
  (fn [request]
    (handler (assoc-in request [:user]
                       (cond (nil? authenticate) nil
                             :default (authenticate request))))))


(defn dispatch [request]
  ;; (println "*** tao.core/dispatch request = " request)
  
  (let [request-roles []
        match (routing/match (request :uri)
                             (request :request-method)
                             request-roles)]
    
      ;; invoke the handler and convert back to ring map
      (response/proxy-to-ring
        ((:handler match) request))))



(defn- set-option! [var-to-alter val]
  (if (nil? val) 
    (throw (Exception. (str "*** :" 
                            (:name (meta var-to-alter)) 
                            " *** must be set in settings map!")))
    (alter-var-root var-to-alter (fn [f] val))))

(defn init [settings dispatch]
  (println "*** now initializing tao settings *** ")
  
  (set-option! #'routing/routes
               (settings :routes))
  
  (set-option! #'routing/not-found-handler
               (settings :not-found-handler))
  
  (set-option! #'routing/not-authorized-handler 
               (settings :not-authorized-handler))
  
  (set-option! #'response/content-type-default 
               (settings :content-type-default))
  
  (println "*** settings initialization is complete *** ")
  
  dispatch)





