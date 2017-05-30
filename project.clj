(defproject paska "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [duct/core "0.3.3"]
                 [duct/module.logging "0.2.0"]
                 [duct/module.web "0.5.0"]
                 [duct/module.ataraxy "0.1.4"]
                 [duct/module.sql "0.2.1"]
                 [org.xerial/sqlite-jdbc "3.16.1"]]
  :plugins [[duct/lein-duct "0.9.0-alpha8"]]
  :main ^:skip-aot paska.main
  :duct {:config-paths ["resources/paska/config.edn"]}
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["duct" "compile"]]
  :profiles
  {:dev     [:project/dev :profiles/dev]
   :repl    {:prep-tasks   ^:replace ["javac" "compile"]
             :repl-options {:init-ns user}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.2.0"]
                                   [eftest "0.3.0"]
                                   [kerodon "0.8.0"]]}})
