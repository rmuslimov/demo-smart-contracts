(ns user
  (:require [figwheel-sidecar.repl-api]))


(defn start-cljs-dev! []
  "After REPL starts with function need to be called. To converr regular REPL
   to CLJS REPL with figwheel on connected to application."
  (figwheel-sidecar.repl-api/start-figwheel!
    (figwheel-sidecar.config/fetch-config)
    "dev")
  (figwheel-sidecar.repl-api/cljs-repl "dev"))

;; (mount.core/stop)
