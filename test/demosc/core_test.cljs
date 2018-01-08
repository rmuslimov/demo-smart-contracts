(ns demosc.core-test
  (:require [cljs.test :refer [deftest is testing]]
            [mount.core :as mount]))

;; (declare start)
;; (declare stop)
;; (mount/defstate core-test-system :start (start) :stop (stop core-test-system))

(deftest test-trivial
  (testing "Something super trivial"
    (is (= 1 1))))
