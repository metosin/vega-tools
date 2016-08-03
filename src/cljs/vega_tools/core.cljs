(ns vega-tools.core
  "Entry points to Vega runtime."
  (:require cljsjs.vega
            [vega-tools.validate :refer [check]]
            [promesa.core :as p]))

(defn parse
  "Parse a Vega specification.

  Returns a Promesa promise that resolves to a chart constructor, or rejects
  with a parsing error."
  ([spec] (parse spec nil))
  ([spec config]
   (p/promise (fn [resolve reject]
                (js/vg.parse.spec (clj->js spec) (clj->js config)
                                  (fn [error chart]
                                    (if error
                                      (reject error)
                                      (resolve chart))))))))

(defn validate
  "Validate a Vega specification.

  Return a Promesa promise that resolves to the spec itself, or rejects with a
  validation error."
  [spec]
  (p/promise (fn [resolve reject]
               (if-let [error (check spec)]
                 (reject error)
                 (resolve spec)))))

(defn validate-and-parse
  "Validate and parse a Vega specification.

  Returns a Promise promise that resolves to a chart constructor, or rejects
  with a validation/parsing error."
  ([spec] (validate-and-parse spec nil))
  ([spec config]
   (->> (validate spec)
        (p/mapcat #(parse % config)))))
