(ns tao.core
  (:require [tao-core.core :as tao-core]
            [ring.middleware.params :as ring-params]
            [ring.middleware.keyword-params :as ring-keyword-params]
            [ring.middleware.stacktrace :as ring-stacktrace]
            [ring.middleware.resource :as ring-resource]
            [ring.middleware.file-info :as ring-file-info]
            ))

;; need to figure out better name than core for this namespace!!!


(defn wrap-user
  "Uses the authenticate handler to lookup and set the user making this request."
  [handler authenticate]
  (fn [request]
    (handler (assoc-in request [:tao :user]
                       (cond (nil? authenticate) nil
                             :default (authenticate request))))))


(defn dispatch [ctx]
  ;; (println "*** tao.core/dispatch ctx = " ctx)
  (tao-core/tao-to-ring-response
   
   (let [handler-info (tao-core/dispatch ctx)
         handler (-> handler-info :handler second)
         ctx (-> handler-info :ctx)]

     ;; check handler for other than :http and disallow?
     ;; don't forget to remove tao internal tracking information
     ;; pass nice keys to handler {:user 'bob :params 'all-params :request 'ring-request

     (handler ctx)

     )))


(defn init [opts]
  (tao-core/init opts)
  (-> #'dispatch
      (wrap-user (opts :authenticate))

      ;; wrap-redirects, wrap-blacklist, wrap-whitelist

      (ring-resource/wrap-resource "public")
      (ring-file-info/wrap-file-info)
      
      (ring-keyword-params/wrap-keyword-params)
      (ring-params/wrap-params)
      

      (ring-stacktrace/wrap-stacktrace)
      
      ) )