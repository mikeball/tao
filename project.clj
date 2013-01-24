(defproject tao "0.0.1"
  :description "A clojure web framework."

  :url "http://github.com/mikeball/tao"
  
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.4.0"]
                 [ring/ring-core "1.1.0"]
                 
                 [ring/ring-jetty-adapter "1.1.0"]
                 [clout "1.1.0"]
                 
                 ]

  :profiles {:dev {:dependencies [[ring/ring-devel "1.1.0"]]}})
