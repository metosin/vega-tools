(set-env!
  :source-paths #{"src/clj" "src/cljs" "test/clj"}
  :resource-paths #{"resources" "test/resources"}
  :dependencies '[[org.clojure/clojure         "1.8.0"     :scope "provided"]
                  [org.clojure/clojurescript   "1.9.89"    :scope "provided"]
                  [adzerk/boot-cljs            "1.7.228-1" :scope "test"]
                  [adzerk/boot-test            "1.1.2"     :scope "test"]
                  [crisptrutski/boot-cljs-test "0.2.1"     :scope "test"]
                  [cljsjs/tv4                  "1.2.7-0"]])

(require
 '[adzerk.boot-cljs :refer [cljs]]
 '[adzerk.boot-test :refer :all]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]])

(deftask testing []
  (merge-env! :source-paths #{"test/cljs"})
  identity)

(deftask dev []
  (comp (cljs)))

(deftask test-once []
  (comp (testing)
        (test-cljs)))

(deftask test-auto []
  (comp (testing)
        (watch)
        (test-cljs)))
