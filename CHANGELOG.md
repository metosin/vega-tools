# Unreleased

* Hiccup-style syntax for the expression language.

# 0.2.0 (2016-10-07)

* **BREAKING**: Wrap the chart constructor, so you do not need to call `clj->js`/`#js` when creating the chart.
* Use [ajv](https://github.com/epoberezkin/ajv) instead of [tv4](https://github.com/geraintluff/tv4) for JSON Schema validation.
  * Validation is much faster which is the reason for the change.
  * **BREAKING:** Error maps do not contain `:code` (numeric error code) anymore.
  * Error maps now contain `:keyword` with the failed validation keyword.

# 0.1.0 (2016-08-04)

Initial release.
