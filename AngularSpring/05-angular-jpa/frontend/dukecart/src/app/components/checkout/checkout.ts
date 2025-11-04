import { Component, inject, OnInit } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LovService } from '../../services/lov.service';
import { Country } from '../../model/country';
import { State } from '../../model/state';
import { DukeValidatorUtil } from '../../shared/util/form.util';
import { CartService } from '../../services/cart.service';


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
  private cartService = inject(CartService);

  // FromBuilder is used to build a FromGroup
  formBuilder = inject(FormBuilder);

  // Instance variables
  // ------------------
  formCheckout: FormGroup;
  totalQuantity: number = 0;
  totalPrice: number = 0.0;

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
            firstName: DukeValidatorUtil.get({"minLength":2, "notOnlyWhitespace":true}),
            lastName:  DukeValidatorUtil.get({"minLength":2, "notOnlyWhitespace":true}),
            email:     DukeValidatorUtil.get({"regex":"[a-z0-9_.-]+@[a-z0-9_-]+\.[a-z]{2,4}"}),
          }),
        shippingAddress: this.formBuilder.group
          ({
            street:  DukeValidatorUtil.get({"minLength":2, "notOnlyWhitespace":true}),
            city:    DukeValidatorUtil.get({"minLength":2, "notOnlyWhitespace":true}),
            state:   DukeValidatorUtil.get({}),
            country: DukeValidatorUtil.get({}),
            zipcode: DukeValidatorUtil.get({"regex":"[0-9]{6}"})
          }),
        billingAddress: this.formBuilder.group
          ({
            street:  DukeValidatorUtil.get({"minLength":2, "notOnlyWhitespace":true}),
            city:    DukeValidatorUtil.get({"minLength":2, "notOnlyWhitespace":true}),
            state:   DukeValidatorUtil.get({}),
            country: DukeValidatorUtil.get({}),
            zipcode: DukeValidatorUtil.get({"regex":"[0-9]{6}"})
          }),
        creditCard: this.formBuilder.group
          ({
            cardType:        DukeValidatorUtil.get({}),
            nameOnCard:      DukeValidatorUtil.get({"regex":"[a-zA-Z. ]{2,}", "notOnlyWhitespace":true}),
            cardNumber:      DukeValidatorUtil.get({"regex":"[0-9]{16}"}),
            securityCode:    DukeValidatorUtil.get({"regex":"[0-9]{3}"}),
            expirationMonth: DukeValidatorUtil.get({}),
            expirationYear:  DukeValidatorUtil.get({})
          }),
      });
  }

  ngOnInit(): void
  {
    this.refreshCountryLOV ();
    this.refreshCart();
    console.log("[Checkout] Intialized from CartService totalPrice=" + this.totalPrice + " totalQuantity=" + this.totalQuantity);
    this.cartService.subjectCartUpdate.subscribe(data => {
      this.refreshCart();
      console.log("[Checkout] subscriber notified by CartService totalPrice=" + this.totalPrice + " totalQuantity=" + this.totalQuantity);
    });

  }

  // Getter methods for fields
  // -------------------------
  // The <ng-container></ng-container> elements of the HTML view shall be referencing the getter methods.
  // Eg: <ng-container *ngTemplateOutlet="tplValidation; context: {fcName:'City', fc:shippingAddressCity}"></ng-container>
  get firstName() { return this.formCheckout.get("customer.firstName"); }
  get lastName()  { return this.formCheckout.get("customer.lastName"); }
  get email()     { return this.formCheckout.get("customer.email"); }

  get shippingAddressStreet()  { return this.formCheckout.get("shippingAddress.street"); }
  get shippingAddressCity()    { return this.formCheckout.get("shippingAddress.city"); }
  get shippingAddressState()   { return this.formCheckout.get("shippingAddress.state"); }
  get shippingAddressCountry() { return this.formCheckout.get("shippingAddress.country"); }
  get shippingAddressZipcode() { return this.formCheckout.get("shippingAddress.zipcode"); }

  get billingAddressStreet()   { return this.formCheckout.get("billingAddress.street"); }
  get billingAddressCity()     { return this.formCheckout.get("billingAddress.city"); }
  get billingAddressState()    { return this.formCheckout.get("billingAddress.state"); }
  get billingAddressCountry()  { return this.formCheckout.get("billingAddress.country"); }
  get billingAddressZipcode()  { return this.formCheckout.get("billingAddress.zipcode"); }

  get cardType()        { return this.formCheckout.get("creditCard.cardType"); }
  get nameOnCard()      { return this.formCheckout.get("creditCard.nameOnCard"); }
  get cardNumber()      { return this.formCheckout.get("creditCard.cardNumber"); }
  get securityCode()    { return this.formCheckout.get("creditCard.securityCode"); }
  get expirationMonth() { return this.formCheckout.get("creditCard.expirationMonth"); }
  get expirationYear()  { return this.formCheckout.get("creditCard.expirationYear"); }


  // LOVs
  // ----

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

  private refreshCart (): void
  {
    this.totalPrice = this.cartService.totalPrice;
    this.totalQuantity = this.cartService.totalQuantity;
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

  /**
   * Copy shipping address to billing if checked
   * Reset billing address if unchecked.
   */
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
    if (this.formCheckout.invalid)
    {
      // Mark all elements as modfied to trigger validation
      this.formCheckout.markAllAsTouched();
    }
    console.log("customer.email=" + this.formCheckout.get("customer")!.value.email);
    console.log("shippingAddress.country=" + this.formCheckout.get("shippingAddress")!.value.country.name);
    console.log("shippingAddress.state=" + this.formCheckout.get("shippingAddress")!.value.state.name);
    console.log("billingAddress.country=" + this.formCheckout.get("billingAddress")!.value.country.name);
    console.log("billingAddress.state=" + this.formCheckout.get("billingAddress")!.value.state.name);

  }
}
