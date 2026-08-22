"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
// Syntax: import {<class...>} from "<relative-path-to-ts-file>"
// Note that the <path-to-ts-file> shall not have the extension of the file
var Customer_1 = require("./Customer");
// Creating objects
var customer = new Customer_1.Customer("Raghu", "Nandan");
// NOTE: Syntax is like assignment. However, we are invoking the set accessor (setter).
customer.lastName = "Seshadri";
// NOTE: The get accessor is invoked with syntax like accessing a public variable.
console.log("Customer=".concat(customer.firstName, ".").concat(customer.lastName));
