(ns demosc.core
  (:require [mount.core :as mount]
            [demosc.smart-contracts]
            [district.server.smart-contracts :as contracts]
            [district.server.web3 :refer [web3]]
            [cljs-web3.eth :as web3-eth]))

(defn get-admin []
  (first (web3-eth/accounts @web3)))

(declare start)
(declare stop)
(mount/defstate deployer
  :start (start (mount/args)) :stop (stop deployer))

(defn start [args]
  "Deploy all contracts from scratch here."
  (contracts/deploy-smart-contract! :foo {:arguments [(get-admin)]}))

(defn stop [deployer]
  nil)

(defn -main []
  (-> (mount/with-args
        {:web3 {:port 8545}
         :smart-contracts {:contracts-var #'demosc.smart-contracts/smart-contracts
                           :print-gas-usage? true
                           :auto-mining? true}})
      (mount/start)))

(set! *main-cli-fn* -main)

(comment
  (mount/stop)

  (contracts/contract-address :foo)
  (contracts/deploy-smart-contract! :foo {:arguments [(get-admin)]})

  (contracts/contract-call :foo :getValue "key")
  (contracts/contract-call :foo :getAdmin)
  (contracts/contract-call :foo :tryadd 3 4)
  (contracts/contract-call :foo :setValue "key" 22 {:from (get-admin)})

  )
