(defproject demosc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [mount "0.1.11"]

                 [district0x/district-server-web3 "1.0.1"]
                 [district0x/district-server-smart-contracts "1.0.3"]]
  :clean-targets ^{:protect false} ["target"]
  :target-path "target/%s"
  :plugins [[lein-cljsbuild "1.1.7"  :exclusions [[org.clojure/clojure]]]
            [lein-figwheel "0.5.13"]]
  :cljsbuild {:builds
              [{:id "deploy"
                :source-paths ["dev" "src" "test"]
                :figwheel true
                :compiler {:main demosc.core
                           :output-to "target/deploy/deploy.js"
                           :output-dir "target/deploy/js"
                           :target :nodejs,
                           :optimizations :none,
                           :verbose false
                           :source-map true}}
               {:id "dev"
                :source-paths ["dev" "src" "test"]
                :figwheel true
                :compiler {:main demosc.starter
                           :output-to "target/dev/runtests.js"
                           :output-dir "target/dev/js"
                           :target :nodejs,
                           :optimizations :none,
                           :verbose false
                           :source-map true}}
               {:id "prod"
                :source-paths ["test"]
                :compiler {:main demosc.starter
                           :output-to "target/prod/runtests.js"
                           :output-dir "target/prod/js"
                           :target :nodejs,
                           :verbose false}}
               ]}
  :profiles {:dev
             {:dependencies [[com.cemerick/piggieback "0.2.2"]
                             [figwheel-sidecar "0.5.13"]
                             [org.clojure/tools.nrepl "0.2.13"]]
              :source-paths ["dev" "test"]
              :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
              :resource-paths ["resources"]}})
