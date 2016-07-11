(set-env!
  :source-paths #{"src/clj" "src/cljs"}
  :resource-paths #{"src/clj" "src/cljs" "resources"}
  :dependencies '[[org.clojure/clojure         "1.8.0"     :scope "provided"]
                  [org.clojure/clojurescript   "1.9.89"    :scope "provided"]
                  [adzerk/boot-cljs            "1.7.228-1" :scope "test"]
                  [adzerk/boot-test            "1.1.2"     :scope "test"]
                  [crisptrutski/boot-cljs-test "0.2.1"     :scope "test"]
                  [cljsjs/tv4                  "1.2.7-0"]])

(def +version+ "0.1.0-SNAPSHOT")

(require
 '[adzerk.boot-cljs :refer [cljs]]
 '[adzerk.boot-test :refer :all]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]])

(task-options!
 pom {:project 'miikka/vega-tools
      :version +version+
      :description "Utilities for working with Vega specifications"
      :license {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}
      :url "https://github.com/miikka/vega-tools/"
      :scm {:url "https://github.com/miikka/vega-tools/"}})

(deftask testing []
  (merge-env! :source-paths #{"test/cljs" "test/clj"}
              :resource-paths #{"test/resources"})
  identity)

(deftask test-clj []
  (comp (testing)
        (test)))

(deftask test-once []
  (comp (testing)
        (test-cljs)))

(deftask test-auto []
  (comp (testing)
        (watch)
        (test-cljs)))

(deftask build []
  (comp
   (pom)
   (jar)
   (install)))
