(defproject tao "0.0.1-SNAPSHOT"
  :description "A web framework for clojure based on ring."

  :url "http://www.taoclj.com/"
  
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.4.0"]
                 [ring/ring-core "1.1.0"]
                 [ring/ring-jetty-adapter "1.1.0"]

                 ;; only here to lein checkouts
                 [clout "1.1.0"]
                 ]

  :profiles {:dev {:dependencies [[ring/ring-devel "1.1.0"]]}}
  )
