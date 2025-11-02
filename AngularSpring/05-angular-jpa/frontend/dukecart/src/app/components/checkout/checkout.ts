import { Component, inject, OnInit } from '@angular/core';
import { CommonModule, DecimalPipe, TitleCasePipe } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { LovService } from '../../services/lov.service';
import { Country } from '../../model/country';
import { State } from '../../model/state';

@Component({
  selector: 'app-checkout',
  imports: [CommonModule, ReactiveFormsModule, DecimalPipe],
  templateUrl: './checkout.html',
  styleUrl: './checkout.css'
})
export class Checkout implements OnInit
{

  // Dependency Injection
  // ---------------------
  private lovService = inject(LovService);

  // FromBuilder is used to build a FromGroup
  formBuilder = inject(FormBuilder);

  // Instance variables
  // ------------------
  formCheckout: FormGroup;
  totalQuantity: number = 0;
  totalPrice: number = 0;

  // UI Variables
  // ------------
  currentYear         :number = new Date().getFullYear();
  listMonthsThisYear  :number[] = [];
  listAllMonths       :number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
  listExpirationMonth :number[] = this.listAllMonths;
  listExpirationYear  :number[] = [];

  countryCodeBilling :string = "";
  countryCodeShipping:string = "";
  listStateBilling   :State[] = [];
  listStateShipping  :State[] = [];
  listCountry :Country[] = [];

  constructor ()
  {
    // Init with leftover months in this year
    const startMonth = new Date().getMonth();
    for (let i = startMonth; i < 12; ++i)
      this.listMonthsThisYear.push(i);

    // Init with 10 years from current year
    const startYear = new Date().getFullYear();
    for (let i = 0; i < 10; ++i)
      this.listExpirationYear.push(startYear + i);

    // Provide the JSON structure to build a FromGroup object
    this.formCheckout = this.formBuilder.group
      ({
        customer: this.formBuilder.group
          ({
            firstName: [''],
            lastName: [''],
            email: ['']
          }),
        shippingAddress: this.formBuilder.group
          ({
            street: [''],
            city: [''],
            state: [''],
            country: [''],
            zipcode: ['']
          }),
        billingAddress: this.formBuilder.group
          ({
            street: [''],
            city: [''],
            state: [''],
            country: [''],
            zipcode: ['']
          }),
        creditCard: this.formBuilder.group
          ({
            cardType: [''],
            nameOnCard: [''],
            cardNumber: [''],
            securityCode: [''],
            expirationMonth: [''],
            expirationYear: ['']
          }),
      });
  }

  ngOnInit(): void
  {
    this.refreshCountryLOV ();
  }

  doBillingCountryChange()
  {
    // NOTE: The syntax for getting the country from the formCheckout
    this.countryCodeBilling = this.formCheckout.get("billingAddress")?.value.country.code;
    this.refreshBillingStateLOV();
  }

  doShippingCountryChange()
  {
    // NOTE: The syntax for getting the country from the formCheckout
    this.countryCodeShipping = this.formCheckout.get("shippingAddress")?.value.country.code;
    this.refreshShippingStateLOV();
  }

  private refreshCountryLOV (): void
  {
    this.lovService.getCountries().subscribe (data =>
    {
      this.listCountry = data._embedded.countries;
    });
  }

  private refreshBillingStateLOV (): void
  {
    if (this.countryCodeBilling == "")
      return;

    this.lovService.getStatesInCountry(this.countryCodeBilling).subscribe (data =>
    {
      this.listStateBilling = data._embedded.states;
    });
  }

  private refreshShippingStateLOV (): void
  {
    if (this.countryCodeShipping == "")
      return;

    this.lovService.getStatesInCountry(this.countryCodeShipping).subscribe (data =>
    {
      this.listStateShipping = data._embedded.states;
    });
  }

  doCopyShippingAddressToBillingAddress(event: Event)
  {
    const isChecked = (event.target as HTMLInputElement).checked;
    console.log ("Copy address checked=" + isChecked);
    if (isChecked)
    {
      this.formCheckout.controls['billingAddress'].setValue(this.formCheckout.controls['shippingAddress'].value);
      this.countryCodeBilling = this.countryCodeShipping;
      this.listStateBilling = this.listStateShipping;
    }
    else
    {
      this.formCheckout.controls['billingAddress'].reset();
      this.listStateBilling = [];
    }
  }

  /**
   * Update the UI instance variables that display months in the year
   */
  doExpirationYearChange(year: string): void
  {
    this.listExpirationMonth = (this.currentYear == Number(year)) ? this.listMonthsThisYear : this.listAllMonths;
  }

  /**
   * The following object shall have the JSON data populated
   * this.formCheckout.get("customer").value
   *
   * An individual field can be obtained as follows
   * this.formCheckout.get("customer").value.email
   */
  doPay(): void
  {
    console.log("Clicked buy");
    console.log("customer.email=" + this.formCheckout.get("customer")!.value.email);
    console.log("shippingAddress.country=" + this.formCheckout.get("shippingAddress")!.value.country.name);
    console.log("shippingAddress.state=" + this.formCheckout.get("shippingAddress")!.value.state.name);
    console.log("billingAddress.country=" + this.formCheckout.get("billingAddress")!.value.country.name);
    console.log("billingAddress.state=" + this.formCheckout.get("billingAddress")!.value.state.name);

  }
}
