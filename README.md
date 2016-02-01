# Tao

A routing library with integrated authorization. Also supports web sockets and server sent events routing.



## Usage


```clojure
(require '[taoclj.tao.routing :as tao])


;; write some handlers, for instance the home page...
;; each handler is simply a function that returns a tao or ring response.

(defn home-page [ctx]
  [200 {} (selmer/render-file "ui/home.html" {})])


;; Here's an example routing table, note the grouping of route+roles
(deftable routes

  :public
  ["/login"             {:get auth/login :post auth/login-attempt}]

  :user
  ["/"                  {:get pages/home}]
  ["/products"          {:get products/list}]
  ["/products/:id"      {:get products/detail}] )


;; Write a function to examine a request and if authenticated attach a user map with roles key.
;; It will be attached to the ring request for use in handlers.

(defn authenticate [ctx]
  ;; if authenticated as bob in the :user role ...
  {:name "bob"
   :roles [:user]})


(def dispatch
    (-> (fn-dispatch {:routes          routes
                      :content-type    "text/html;charset=utf-8"
                      :not-found       pages/not-found
                      :not-authorized  pages/not-authorized
                      :authenticate    authenticate})
        ; wrap-keyword-params
        (wrap-params)
        (wrap-cookies)
        (wrap-resource "public")
        (wrap-content-type)
        (wrap-not-modified)))


(defn -main []
  (run-jetty dispatch {:port 8080}))



```



## License

Copyright © 2016 Michael Ball

Distributed under the Eclipse Public License, the same as Clojure.
