(defproject org.taoclj/tao "0.0.2-SNAPSHOT"
  :description "An http routing and authorization library"

  :url "http://github.com/mikeball/tao"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.8.0"]
                 [potemkin "0.3.4"]
                 [org.taoclj/via "0.0.1-SNAPSHOT"]
                 [org.taoclj/ticket "0.0.1"]
                 [org.mindrot/jbcrypt "0.3m"]])
