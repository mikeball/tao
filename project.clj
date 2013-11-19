(defproject tao "0.0.1"
  :description "An http routing and authorization library"

  :url "http://github.com/mikeball/tao"
  
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clout "1.1.0"]
                 [ring/ring-core "1.2.1"]]

  :profiles {:dev {:dependencies [[ring/ring-devel "1.2.0-RC1"]]}})
