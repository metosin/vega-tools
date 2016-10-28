(ns vega-tools.expr-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [vega-tools.expr :refer [expr]]))

(deftest test-expr
  (testing "Primitive expressions"
    (is (= (expr :datum) "datum"))
    (is (= (expr {}) "{}"))
    (is (= (expr 123) "123")))
  (testing "Functions"
    (is (= (expr [:if :datum.x 1 0]) "if(datum.x,1,0)"))))
