(ns demosc.starter
  (:require [cljs.nodejs :as nodejs]
            [cljs.test :refer [run-tests]]
            [mount.core :as mount]
            [demosc.core-test]))

(nodejs/enable-util-print!)

(mount/in-cljc-mode)

(.on js/process "uncaughtException" #(js/console.error %))
(set! (.-error js/console) (fn [x] (.log js/console x)))

(defn execute-tests []
  (run-tests 'demosc.core-test))

(defn -main []
  (println "Running tests...")
  (execute-tests))

;; code taken from https://clojurescript.org/tools/testing
;; (defmethod cljs.test/report [:cljs.test/default :end-run-tests] [m]
;;   (if (cljs.test/successful? m)
;;     (println "Success!")
;;     (println "FAIL")))

(set! *main-cli-fn* -main)
