riemann-kafka
=============

[![Build Status](https://secure.travis-ci.org/pyr/riemann-kafka.png)](http://travis-ci.org/pyr/riemann-kafka)


kafka consumer and producer support for riemann

## Synopsis

In riemann.config

```clojure

(load-plugins)

(kafka/kafka-consumer {:topic "events"
                       :zookeeper.connect "localhost:181"
                       :group.id "riemann.consumer"
                       :auto.offset.reset "smallest"
                       :auto.commit.enable "false"})


(let [producer (kafka/kafka-producer
                 {:topic "expired"
                  :metadata.broker.list "localhost:9091"})]
  (streams
    prn
    (expired producer)))
```

### Customized Encoder and Decoder

The riemann-kafka producer supports customized encoder to encode events.  
A encoder is a function that expects a sequence of events and returns a Bytes object.  
If you don't specify a :encoder, riemann.common/encode is used, which turns events into a protobuf.

```clojure

(defn my-json-encoder
  "Encode events into json, then toBytes"
  [events]
  (.getBytes (cheshire.core/encode events)))

(let [producer (kafka/kafka-producer
                 {:topic "expired"
                  :metadata.broker.list "localhost:9091"
                  :encoder my-json-encoder})]
  (streams
    prn
    (expired producer)))

```

The riemann-kafka consumer supports customized decoder to decode messages.  
A decoder gets a message from kafka as a Byte object, then returns a sequence of events.  
If you don't specify a :decoder, the default decoder expects the incoming message to be a riemann protobuf object.

```clojure

(defn my-json-decoder
  "Decode kafka message into a riemann event"
  [input]
  ; input is a single kafka message in Bytes
  ; If the payload is a string, it needs to be reverted by `(String. input)`
  ; Return SHOULD be a seq of riemann events
  (let [decoded-msg (cheshire.core/parse-string (String. input) true)]
    (map riemann.common/event decoded-msg)))

(kafka/kafka-consumer {:topic "events"
                       :zookeeper.connect "localhost:181"
                       :group.id "riemann.consumer"
                       :auto.offset.reset "smallest"
                       :auto.commit.enable "false"
                       :decoder my-json-decoder})
```

## Installing

You will need to build this module for now and push it on riemann's classpath, for this
you will need a working JDK, JRE and [leiningen](http://leiningen.org).

First build the project:

```
lein uberjar
```

The resulting artifact will be in `target/riemann-kafka-standalone-0.1.0.jar`.
You will need to push that jar on the machine(s) where riemann runs, for instance, in
`/usr/lib/riemann/riemann-kafka.jar`.

If you have installed riemann from a stock package you will only need to tweak
`/etc/default/riemann` and change
the line `EXTRA_CLASSPATH` to read:

```
EXTRA_CLASSPATH=/usr/lib/riemann/riemann-kafka.jar
```

You can then use exposed functions, provided you have loaded the plugin in your configuration.



