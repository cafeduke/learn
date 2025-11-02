import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Country } from '../model/country';
import { State } from '../model/state';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LovService
{
  private static readonly BASE_URL_COUNTRIES = "http://localhost:9090/dukecart/countries";

  private static readonly BASE_URL_STATES = "http://localhost:9090/dukecart/states";

  // Dependency injection
  private httpClient = inject(HttpClient);

  constructor ()   {  }

  getCountries (): Observable<CountryListJSON>
  {
    const url = `${LovService.BASE_URL_COUNTRIES}`;
    return this.getCountriesFromUrl(url);
  }

  getStatesInCountry (countryCode: string): Observable<StateListJSON>
  {
    const url = `${LovService.BASE_URL_STATES}/search/findByCountryCode?code=${countryCode}`;
    return this.getStatesFromUrl(url);
  }

  /**
   * ----------------------------------------------------------------------------------------------
   * Private methods
   * ----------------------------------------------------------------------------------------------
   */

  private getCountriesFromUrl (url: string): Observable<CountryListJSON>
  {
    return this.httpClient
      .get<CountryListJSON>(url)
      .pipe(map(response => response));
  }

  private getStatesFromUrl (url: string): Observable<StateListJSON>
  {
    return this.httpClient
      .get<StateListJSON>(url)
      .pipe(map(response => response));
  }
}

interface StateListJSON
{
  _embedded:
  {
    states: State[];
  },
}

interface CountryListJSON
{
  _embedded:
  {
    countries: Country[];
  },
}
