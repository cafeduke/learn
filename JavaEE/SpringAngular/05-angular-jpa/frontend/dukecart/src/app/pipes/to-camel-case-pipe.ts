import { Pipe, PipeTransform, inject } from '@angular/core';

@Pipe({
  name: 'toCamelCase',
  standalone: true
})
export class ToCamelCasePipe implements PipeTransform
{
  transform(value: string): string
  {
    if (typeof value !== 'string')
      return "";

    let result = "";
    value.split(/\s+/).forEach((token: string, index: number) =>
    {
      result = result
        + ((index == 0) ? token.charAt(0).toLowerCase() : token.charAt(0).toUpperCase())
        + token.substring(1);
    });
    return result;
  }
}
