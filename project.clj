(defproject cryptocurrency "0.1.0-SNAPSHOT"
  :description "fariba coins"
  :url "none"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0-beta1"]
                 [com.google.code.gson/gson "2.8.5"]]
  :main ^:skip-aot cryptocurrency.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
