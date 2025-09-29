/**
 * Variables -- Strong typed
 * ----------------------------------------------------------------------------------------------------
 * Note: Variable takes values only of the type
 */
let found: boolean = true;
let grade: number = 88.6;
let firstName: string = "Raghu";

// Concatenation
console.log("Hello, " + firstName);

// Backticks to dereference variables
console.log(`Hello, ${firstName}`);

/**
 * Variables -- Strong typed context based
 * ----------------------------------------------------------------------------------------------------
 * Note: Variable takes values only of the type. Type type is determined by init value.
 */
let num = 100;
let str = "Hello";
console.log("Types: num=" + typeof (num) + " str=" + typeof (str));
console.log(`Types: num=${typeof (num)} str=${typeof (str)}`);

// Results in error: Type 'string' is not assignable to type 'number'
// num = "Bye"

/**
 * Variables -- AnyType
 * ----------------------------------------------------------------------------------------------------
 * Note: Variable takes values only of any type
 */
let myvar: any = 10;
console.log(`TypeAny: myvar=${myvar}`);
myvar = "Raghu";
console.log(`TypeAny: myvar=${myvar}`);
