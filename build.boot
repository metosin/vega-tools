(set-env!
  :source-paths #{"src/clj" "src/cljs"}
  :resource-paths #{"src/clj" "src/cljs" "resources"}
  :dependencies '[[org.clojure/clojure         "1.8.0"     :scope "provided"]
                  [org.clojure/clojurescript   "1.9.89"    :scope "provided"]
                  [adzerk/boot-cljs            "1.7.228-1" :scope "test"]
                  [metosin/boot-alt-test       "0.1.0"     :scope "test"]
                  [crisptrutski/boot-cljs-test "0.2.1"     :scope "test"]
                  [cljsjs/tv4                  "1.2.7-0"]
                  [cljsjs/vega                 "2.6.0-0"]
                  [funcool/promesa             "1.4.0"]])

(def +version+ "0.2.0-SNAPSHOT")

(require
 '[adzerk.boot-cljs :refer [cljs]]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]]
 '[metosin.boot-alt-test :refer [alt-test]])

(task-options!
 pom {:project 'metosin/vega-tools
      :version +version+
      :description "Utilities for working with Vega specifications"
      :license {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}
      :url "https://github.com/metosin/vega-tools/"
      :scm {:url "https://github.com/metosin/vega-tools/"}})

(deftask testing []
  (merge-env! :source-paths #{"test/cljs" "test/clj"}
              :resource-paths #{"test/resources"})
  identity)

(deftask run-tests
  [f fail bool]
  (comp (testing)
        (alt-test :fail fail)
        (test-cljs :exit? fail)))

(deftask dev []
  (comp (testing)
        (watch)
        (run-tests)))

(deftask build []
  (comp
   (pom)
   (jar)
   (install)))

(deftask deploy []
  (comp
   (build)
   (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))
