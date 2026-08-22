import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Product } from '../model/product';

@Injectable({ providedIn: 'root' })
export class ProductService
{
  private static readonly BASE_URL = "http://localhost:9090/dukecart/products";

  // Dependency injection
  private httpClient = inject(HttpClient);

  constructor ()   {  }

  /**
   * Return an Observable array of Products based on search string
   */
  searchProductsPaginate (searchKey: string, pageIndex: number, pageSize: number): Observable<ProductListJSON>
  {
    const url = `${ProductService.BASE_URL}/search/findByNameContaining?name=${searchKey}&page=${pageIndex}&size=${pageSize}`;
    return this.getProductsFromUrl(url);
  }

  /**
   * Return an Observable array of Product for the given category and page details
   */
  getProductsByCategoryIdPaginate (categoryId: number, pageIndex: number, pageSize: number): Observable<ProductListJSON>
  {
    const url = `${ProductService.BASE_URL}/search/findByCategoryId?categoryId=${categoryId}&page=${pageIndex}&size=${pageSize}`;
    return this.getProductsFromUrl(url);
  }

  /**
   * Return an Observable Product given the product id
   */
  getProductById (productId: number): Observable<Product>
  {
    const url = `${ProductService.BASE_URL}/${productId}`;
    return this.getProductFromUrl(url);
  }

  /**
   * ----------------------------------------------------------------------------------------------
   * Private methods
   * ----------------------------------------------------------------------------------------------
   */

  private getProductsFromUrl (url: string): Observable<ProductListJSON>
  {
    return this.httpClient
      .get<ProductListJSON>(url)
      .pipe(map(response => response));
  }

  private getProductFromUrl (url: string): Observable<Product>
  {
    return this.httpClient.get<Product>(url);
  }

}

// Spring Data REST enforces Hypertext Application Language (HAL) convention -- which structures API responses to include links and embedded resources.
interface ProductListJSON
{
  _embedded:
  {
    products: Product[];
  },
  page:
  {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number;
  }
}
