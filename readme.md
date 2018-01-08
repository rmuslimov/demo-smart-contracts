# Demo smart contracts

A Clojurescript Node.js + mount + SmartContracts template based on `district-server-smartcontracts`.

Very handy for development, contracts building, reloading and running tests with shortcuts. Interop with ethereum with cljs-web3

## Usage

1. Run Emacs REPL (`C-c C-M-J`)
2. Init all emacs helpers code. Go to `cider.el` and call `C-c C-k`.
3. Start testrpc in terminal  (`ganache-cli`)
4. Start node app in another terminal (`node target/dev/runtests.js`)
5. Compiling contracts code can be executed after changes with:

```sh
./compile-solidity.sh
```

or with `C-c C-w`.

6. Contracts can be redeployed with standard reload shortcut `C-x C-c`
7. Tests can be runned `C-c C-t t`. Unfoirtunetely, cider doesn't support tests for clojurescript yet.
