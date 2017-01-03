(ns vega-tools.expr-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [vega-tools.expr :refer [compile-expr] :refer-macros [expr]]))

(deftest test-compile-expr
  (testing "Primitive expressions"
    (is (= (compile-expr :datum) "datum"))
    (is (= (compile-expr {}) "{}"))
    (is (= (compile-expr 123) "123")))
  (testing "Functions"
    (is (= (compile-expr [:if :datum.x 1 0]) "if(datum.x,1,0)"))
    (is (= (compile-expr [:< 1 2 3]) "1<2<3"))))

(deftest test-expr
  (testing "Primitive expressions"
    (is (= "123" (expr 123)))
    (is (= "{}" (expr {})))
    (is (= "datum" (expr :datum)))
  (testing "Functions"
    (is (= "if(datum.x,1,0)" (expr (if :datum.x 1 0))))
    (is (= "if(a<1,x,y)" (expr (if (< :a 1) :x :y)))))
  (testing "Variables"
    (let [x 5]
      (is (= "5" (expr x)))
      (is (= "sin(5)" (expr (sin x))))))))
