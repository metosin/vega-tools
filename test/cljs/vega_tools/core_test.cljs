(ns vega-tools.core-test
  (:require [vega-tools.core :refer [validate-and-parse]]
            [cljs.test :refer-macros [deftest is testing async]]
            [promesa.core :as p])
  (:require-macros [vega-tools.testing :refer [assert-resolved assert-rejected]]))

(deftest test-validate-and-parse-failed
  (async done
    (-> (validate-and-parse {:width "not a number"})
        (assert-rejected)
        (p/finally #(done)))))

(deftest test-validate-and-parse-success
  (async done
   (-> (validate-and-parse {})
       (assert-resolved)
       (p/finally #(done)))))
