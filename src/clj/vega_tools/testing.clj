(ns vega-tools.testing
  "Utilities for writing unit tests."
  (:require [cljs.test]))

(defmethod cljs.test/assert-expr 'thrown-with-ex-data? [menv msg form]
  (let [data (second form)
        body (nthnext form 2)]
    `(try
       ~@body
       (cljs.test/do-report {:type :fail, :message ~msg, :expected '~form, :actual nil})
       (catch :default e#
         (if (= (ex-data e#) ~data)
           (cljs.test/do-report
            {:type :pass, :message ~msg, :expected '~form,:actual e#})
           (cljs.test/do-report
            {:type :fail, :message ~msg, :expected '~form,:actual e#}))
         e#))))
