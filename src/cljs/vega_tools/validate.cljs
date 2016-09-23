(ns vega-tools.validate
  "Tools for validating a Vega specification."
  (:require cljsjs.ajv)
  (:require-macros [vega-tools.macros :refer [inline-resource]]))

(def ^:private vega-schema (js/JSON.parse (inline-resource "vega_tools/vega-schema.json")))
(def ^:private validator (delay (.compile (js/Ajv.) vega-schema)))

(defn ^:private error->map
  [error]
  {:data-path (.-dataPath error)
   :message (.-message error)
   :schema-path (.-schemaPath error)
   :keyword (.-keyword error)})

;; vega-schema extends refs with additional keywords and ajv issues warnings
;; about this. We rebind js/console to supress the warnings. Once
;; <https://github.com/epoberezkin/ajv/issues/303> is resolved, the rebinding
;; hack can be removed.
(defn ^:private quiet-ajv [schema data]
  (with-redefs [js/console #js {:log (constantly nil)}]
    (when-not (@validator (clj->js data))
      (array-seq (.-errors @validator)))))

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
  (when-let [errors (quiet-ajv vega-schema x)]
    (mapv error->map errors)))

(defn validate
  "Throw an exception if x is not a valid Vega specification; otherwise, return
  x.

  The exception's ex-data contains the found problems in the key :errors in the
  same format as returned by check."
  [x]
  (if-let [errors (check x)]
    (throw (ex-info "Invalid Vega specification!" {:errors errors}))
    x))
