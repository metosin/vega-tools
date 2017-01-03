(ns vega-tools.expr
  "Hiccup-style syntax for writing Vega expressions."
  (:require [clojure.string :as string]))

(def ^:private infix-operators #{:< :> :=})

(defn compile-expr [e]
  "Convert e to a Vega expression."
  (cond
    (vector? e)
    (let [function (first e)]
      (if (contains? infix-operators function)
        (str (string/join (name function) (map compile-expr (rest e))))
        (str (name (first e)) "(" (string/join "," (map compile-expr (rest e))) ")")))
    (keyword? e) (name e)
    (and (map? e) (empty? e)) "{}"
    (number? e) (str e)
    :else (throw (ex-info "Unable to convert to Vega expression" e))))

(defn- mangle-expr [e]
  (cond
    (seq? e) (vec (concat [(keyword (first e))] (map mangle-expr (rest e))))
    :else e))

#?(:clj (defmacro expr [body] `(compile-expr ~(mangle-expr body))))
