import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Category } from '../model/category';
import { inject, Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class CategoryService
{
  private static readonly BASE_URL = "http://localhost:9090/dukecart/categories";

  // Dependency injection
  private httpClient = inject(HttpClient);

  constructor ()   {  }

  getCategories (): Observable<Category[]>
  {
    const searchURL = `${CategoryService.BASE_URL}`;
    return this.httpClient
      .get<GetResponse>(searchURL)
      .pipe(map(response => response._embedded.categories));
  }
}

// Spring Data REST enforces Hypertext Application Language (HAL) convention -- which structures API responses to include links and embedded resources.
interface GetResponse
{
  _embedded:
  {
    categories: Category[];
  }
}
