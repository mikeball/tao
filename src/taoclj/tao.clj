(ns taoclj.tao
  (:require [taoclj.junction :as junction]
            [ring.middleware.params :as ring-params]
            [ring.middleware.keyword-params :as ring-keyword-params]
            [ring.middleware.stacktrace :as ring-stacktrace]
            [ring.middleware.resource :as ring-resource]
            [ring.middleware.file-info :as ring-file-info]))

;; perhaps rename wrap-auth or load-user
(defn wrap-user
  "Uses the authenticate handler to lookup and set the user making this request."
  [handler authenticate]
  (fn [request]
    (handler (assoc-in request [:user]
                       (cond (nil? authenticate) nil
                             :default (authenticate request))))))


(defn dispatch [ctx]
  ;; (println "*** tao.core/dispatch ctx = " ctx)
  (junction/proxy-ring-response
   (let [handler-info (junction/dispatch ctx)
         handler (-> handler-info :handler second)
         junction-ctx (-> handler-info :ctx)]

     ;; (println "\n ******  tao/dispatch junction-ctx = " junction-ctx)

     (handler junction-ctx))))


(defn init [opts]
  (junction/init opts))
