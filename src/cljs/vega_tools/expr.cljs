(ns vega-tools.expr
  "Hiccup-style syntax for writing Vega expressions."
  (:require [clojure.string :as string]))

(defn expr [e]
  "Convert e to a Vega expression."
  (cond
    (vector? e) (str (name (first e)) "(" (string/join "," (map expr (rest e))) ")")
    (keyword? e) (name e)
    (and (map? e) (empty? e)) "{}"
    (number? e) (str e)
    :else (throw (ex-info "Unable to convert to Vega expression" e))))
