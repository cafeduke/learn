import { AbstractControl, FormControl, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";

export class DukeValidatorUtil
{
  /**
   * Returns a FormControl object with initialized with {defaultValue} and added validators
   *  - required:true  (Added by default)
   *  - minLength:<min-value-size>
   *  - maxLength:<max-value-size>
   *  - reqex:<value-to-match-regular-expression>
   *
   * @param JSON object with key:value pairs
   * @returns FormControl initialized with defaultValue and added validators
   */
  public static get ({defaultValue, notOnlyWhitespace, minLength, maxLength, regex} : { defaultValue? : string, notOnlyWhitespace?:boolean, minLength? :number, maxLength? :number, regex? : string}): FormControl
  {
    let listValidator: ValidatorFn[] = [Validators.required];

    if (!defaultValue)
      defaultValue = "";

    if(notOnlyWhitespace)
      listValidator.push(DukeValidatorUtil.notOnlyWhitespace);

    if (minLength)
      listValidator.push(Validators.minLength(minLength));

    if (maxLength)
      listValidator.push(Validators.minLength(maxLength));

    if (regex)
      listValidator.push(Validators.pattern(regex));

    return new FormControl(defaultValue, listValidator);
  }

  // Custom validator
  // ----------------

  /**
   * A function that accepts param AbstractControl and returns 'ValidationErrors|null' can be added to ValidatorFn array
   * and is thus a valid validator.
   */
  public static notOnlyWhitespace (control: AbstractControl) : ValidationErrors|null
  {
    if (control.value == null || control.value.trim().length == 0)
      return {'notOnlyWhitespace':true};
    return null;
  }
}
