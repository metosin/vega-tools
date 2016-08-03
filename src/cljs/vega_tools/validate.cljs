(ns vega-tools.validate
  "Tools for validating a Vega specification."
  (:require cljsjs.tv4)
  (:require-macros [vega-tools.macros :refer [inline-resource]]))

(def ^:private vega-schema (js/JSON.parse (inline-resource "vega_tools/vega-schema.json")))

(defn ^:private error->map
  [error]
  {:data-path (.-dataPath error)
   :message (.-message error)
   :schema-path (.-schemaPath error)
   :code (.-code error)})

(defn check
  "Return nil if x is a valid Vega specification; otherwise, return a vector
  of maps describing the failures.

  The maps have the following keys:
  :data-path   -- JSON pointer to the failing data
  :schema-path -- JSON pointer to the failing part of Vega specification schema
  :message     -- human-readable error message
  :code        -- numeric tv4 error code

  For tv4 error codes, see
  <https://github.com/geraintluff/tv4/blob/master/source/api.js>."
  [x]
  (let [result (js/tv4.validateMultiple (clj->js x) vega-schema)]
    (when-not (.-valid result)
      (assert (empty? (.-missing result))
              "Vega spec should not reference unknown schemata.")
      (mapv error->map (.-errors result)))))

(defn validate
  "Throw an exception if x is not a valid Vega specification; otherwise, return
  x.

  The exception's ex-data contains the found problems in the key :errors in the
  same format as returned by check."
  [x]
  (if-let [errors (check x)]
    (throw (ex-info "Invalid Vega specification!" {:errors errors}))
    x))
