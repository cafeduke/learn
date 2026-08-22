/**
 *  Exporting
 *  ---------
 *  A class needs to be exported in order to be acessible via 'import'
 */

export class Customer
{
  /**
   * Parameter properties
   * --------------------
   * Here, the properties _firstName and _lastName as parameters that generates properties with the same name.
   */
  constructor(private _firstName: string, private _lastName: string)
  {
  }

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
    this._lastName = value;
  }

  public toString(): string
  {
    return `${this._firstName}.${this._lastName}`;
  }
}
