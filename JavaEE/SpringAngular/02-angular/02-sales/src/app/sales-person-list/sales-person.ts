export class SalesPerson
{
  // Angular uses public instance variables to remove the need to boilerplate getters and setters
  constructor (public firstName: string, public lastName: string, public email: string, public salesVolume: number) {}
}
