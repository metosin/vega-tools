(ns vega-tools.testing
  "Utilities for writing unit tests."
  (:require [cljs.test]))

;; Derived from
;; <https://github.com/clojure/clojurescript/blob/d06a4f4273a93bd73876867c93e3018069f5836f/src/main/cljs/cljs/test.clj#L113>
(defmethod cljs.test/assert-expr 'thrown-with-ex-data? [menv msg form]
  (let [data (second form)
        body (nthnext form 2)]
    `(try
       ~@body
       (cljs.test/do-report {:type :fail, :message ~msg, :expected '~form, :actual nil})
       (catch :default e#
         (if (= (ex-data e#) ~data)
           (cljs.test/do-report
            {:type :pass, :message ~msg, :expected '~form, :actual e#})
           (cljs.test/do-report
            {:type :fail, :message ~msg, :expected '~form, :actual e#}))
         e#))))
