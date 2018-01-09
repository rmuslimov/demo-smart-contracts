pragma solidity ^0.4.18;

import "Bar.sol";

contract Foo {

    address public admin;
    mapping (bytes32 => uint) public values;

    event IneligibleAdministration(address indexed notAdmin);

    modifier adminOnly {
        if (msg.sender == admin) {
            _;
        } else {
            IneligibleAdministration(msg.sender);
        }
    }

    function Foo(address _admin) public {
        admin = _admin;
    }

    function () public { revert(); } // solhint-disable-line


    function setAdmin(address _admin) public adminOnly {
        admin = _admin;
    }

    function getAdmin() public constant returns (address) {
        return admin;
    }

    function getValue(bytes32 key) public constant returns (uint) {
        return values[key];
    }

    function setValue(bytes32 key, uint value) public adminOnly {
        values[key] = value;
    }

    function tryadd(uint a, uint b) public constant returns (uint) {
        return a + b;
    }

    function library_add(uint a, uint b) public constant returns (uint) {
        return Bar.inc_tryadd(a, b);
    }
}
