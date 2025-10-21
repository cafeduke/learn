import { Injectable, resource } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Product } from '../model/product';

@Injectable({ providedIn: 'root' })
export class ProductService
{
  private static readonly BASE_URL = "http://localhost:9090/dukecart/products";

  constructor (private httpClient: HttpClient)   {  }

  /**
   * Return an Observable array of Products based on search string
   */
  searchProducts (searchKey: string): Observable<Product[]>
  {
    const url = `${ProductService.BASE_URL}/search/findByNameContaining?name=${searchKey}`;
    return this.getProductsFromUrl(url);
  }

  /**
   * Return an Observable array of Product for the given category
   */
  getProductsByCategoryId (categoryId: number): Observable<Product[]>
  {
    const url = `${ProductService.BASE_URL}/search/findByCategoryId?categoryId=${categoryId}`;
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

  private getProductsFromUrl (url: string): Observable<Product[]>
  {
    return this.httpClient
      .get<ProductArrayResponse>(url)
      .pipe(map(response => response._embedded.products));
  }

  private getProductFromUrl (url: string): Observable<Product>
  {
    return this.httpClient.get<Product>(url);
  }

}

// Spring Data REST enforces Hypertext Application Language (HAL) convention -- which structures API responses to include links and embedded resources.
interface ProductArrayResponse
{
  _embedded:
  {
    products: Product[];
  }
}
