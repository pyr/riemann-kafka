(defproject spootnik/riemann-kafka "0.1.3"
  :description "riemann producer and consumer for kafka queues"
  :url "https://github.com/pyr/riemann-kafka"
  :license {:name "MIT License"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [riemann             "0.2.9"]
                 [clj-kafka           "0.3.2"
                  :exclusions [org.slf4j/slf4j-log4j12
                               org.slf4j/slf4j-simple]]])
