(ns demosc.core
  (:require [cljs-web3.core :as web3]
            [cljs-web3.eth :as web3-eth]
            [cljs.nodejs :as nodejs]
            [mount.core :as mount]
            [district.server.smart-contracts :as contracts]
            [district.server.web3 :refer [web3]]

            [demosc.smart-contracts]
            ))

(nodejs/enable-util-print!)

(set! (.-error js/console) (fn [x] (.log js/console x)))

(defn get-admin []
  (first (web3-eth/accounts @web3)))

(defn start [args]
  "Deploy all contracts from scratch here."
  (contracts/deploy-smart-contract! :bar)
  (contracts/deploy-smart-contract!
   :foo
   {:arguments [(get-admin)]
    :placeholder-replacements {"__Bar.sol:Bar___________________________" :bar}})
  )

(mount/defstate deployer :start (start (mount/args)) :stop (fn [deployer] nil))

(defn -main []
  nil)

(set! *main-cli-fn* -main)

(comment
  (-> (mount/with-args
        {:web3 {:port 8545}
         :smart-contracts {:contracts-var #'demosc.smart-contracts/smart-contracts
                           :print-gas-usage? true
                           :auto-mining? true}})
      (mount/start))

  (mount/stop)

  (contracts/deploy-smart-contract! :bar)

  (contracts/contract-address :bar)
  (contracts/deploy-smart-contract! :foo {:arguments [(get-admin)]})

  (contracts/contract-call :foo :getValue "key")
  (contracts/contract-call :foo :getAdmin)
  (contracts/contract-call :foo :tryadd 4 4)
  (contracts/contract-call :foo :library_add 4 4)
  (contracts/contract-call :foo :tryadd 4 4)
  (contracts/contract-call :foo :setValue "key" 22 {:from (get-admin)})

  )
