(ns vega-tools.macros
  (:require [clojure.java.io :as io]))

(defmacro inline-resource
  [path]
  (slurp (io/resource path)))
