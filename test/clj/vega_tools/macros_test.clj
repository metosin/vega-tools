(ns vega-tools.macros-test
  (:require [vega-tools.macros :refer [inline-resource]]
            [clojure.test :refer [deftest is]]))

(deftest test-inline-resource
  (let [text (inline-resource "macro_test.txt")]
    (is (= text "this is a test\n"))))
