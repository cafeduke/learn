// Syntax: import {<class...>} from "<relative-path-to-ts-file>"
// Note that the <path-to-ts-file> shall not have the extension of the file
import {Customer} from "./Customer";

// Creating objects
let customer = new Customer("Raghu", "Nandan");

// NOTE: Syntax is like assignment. However, we are invoking the set accessor (setter).
customer.lastName = "Seshadri";

// NOTE: The get accessor is invoked with syntax like accessing a public variable.
console.log(`Customer=${customer.firstName}.${customer.lastName}`);
