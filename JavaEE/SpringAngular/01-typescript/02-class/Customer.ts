class Customer
{
  /**
   * Parameter properties
   * --------------------
   * Any constructor argument (like _firstName) that is prefixed with an access modifier (like private) becomes a "parameter property".
   * In a parameter property, the user need not
   *  - Declare a class variable (say _myvar)
   *  - Write "this._myvar = myvar" inside the constructor -- This is implicitly done.
   * Here, the properties _firstName and _lastName as parameters that generates properties with the same name.
   */
  constructor(private _firstName: string, private _lastName: string) { }


  /**
   * Getters and Setters accessors
   * -----------------------------
   * NOTE: syntax
   * public get <property-without-underscore> ():<type>
   * public set <property-without-underscore> (value: <type>)
   */

  public get firstName(): string
  {
    return this._firstName;
  }

  public set firstName(value: string)
  {
    this._firstName = value;
  }

  public get lastName(): string
  {
    return this._lastName;
  }

  public set lastName(value: string)
  {
    console.log("[Customer.lastName] Set accessor is invoked");
    this._lastName = value;
  }

  // Override the toString method
  public toString(): string
  {
    return `${this._firstName}.${this._lastName}`;
  }
}

// Creating objects
let customer = new Customer("Raghu", "Nandan");

// NOTE: Syntax is like assignment. However, we are invoking the set accessor (setter).
customer.lastName = "Seshadri";

// NOTE: The get accessor is invoked with syntax like accessing a public variable.
console.log(`${customer}`);
