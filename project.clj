(defproject org.spootnik/riemann-kafka "0.1.1"
  :description "riemann producer and consumer for kafka queues"
  :url "https://github.com/pyr/riemann-kafka"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [riemann             "0.2.7"]
                 [clj-kafka           "0.1.2-0.8"
                  :exclusions [org.slf4j/slf4j-log4j12
                               org.slf4j/slf4j-simple]]])
