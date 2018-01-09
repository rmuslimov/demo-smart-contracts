pragma solidity ^0.4.18;

contract IFoo {
    function tryadd(uint a, uint b) public constant returns (uint);
}

library Bar {

    function inc_tryadd(uint a, uint b) public constant returns (uint c) {
        IFoo self = IFoo(this);
        return self.tryadd(a, b) * 2;
    }
}
