(ns demosc.core-test
  (:require[cljs-web3.core :as web3]
           [cljs-web3.eth :as web3-eth]
            [cljs.test :refer [deftest is testing use-fixtures]]
            [bignumber.core :as bignumber]
            [district.server.smart-contracts :as contracts]
            [district.server.web3 :refer [web3]]))


(def c-call contracts/contract-call)

(def admin (first (web3-eth/accounts @web3)))
(def non-admin (second (web3-eth/accounts @web3)))

;; Fixtures

(defn once-before []
  (c-call :foo :setValue :once 1 {:from admin}))

(use-fixtures :once {:before once-before})

;; Tests

(deftest test-fixtures
  (testing "If fixtures works properly"
    (is
     (= (bignumber/number (c-call :foo :getValue :once)) 1))))

(deftest test-admin-rights
  (testing "Test adminOnly modifier for admin"
    (c-call :foo :setValue :key 3 {:from admin})
    (is
     (= (bignumber/number (c-call :foo :getValue :key)) 3)))

  (testing "Test adminOnly modifier for non-admin"
    (c-call :foo :setValue :key 1 {:from admin})
    (c-call :foo :setValue :key 2 {:from non-admin})
    (is
     (= (bignumber/number (c-call :foo :getValue :key)) 1))))
