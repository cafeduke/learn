import { Injectable} from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { Product } from '../model/product';

@Injectable({
  providedIn: 'root'
})
export class CartService
{
  // Instance variables
  // ------------------
  mapProductQuantity: Map<Product,number>;
  totalQuantity:number = 0;
  totalPrice:number = 0.0;

  // Subjects
  // --------
  // A subject calls its subscribers when an event occurs
  subjectCartUpdate: Subject<void> = new Subject<void>();

  constructor ()
  {
    this.mapProductQuantity = new Map<Product, number>();
  }

  /**
   * Add the product to cart thus incrementing quantity and total price.
   */
  addToCart (product: Product)
  {
    // A new product's quantity=(0+1) and an existing product's quantity=(quantity+1)
    let quantity = (this.mapProductQuantity.get(product) || 0) + 1;
    this.mapProductQuantity.set(product, quantity);

    this.totalQuantity++;
    this.totalPrice += product.unitPrice;

    this.publishCartUpdated ();
  }

  /**
   * Remove product from cart thus decremeting quantity and total price.
   */
  removeFromCart (product: Product)
  {
    if (!this.mapProductQuantity.has(product))
      throw new Error(`[CartService] Product ${product.name} does not exist`);

    // Product exists and its quantity should be 1 or more. Reduce the quantity by 1
    // If the quantity becomes zero, then remove the item from the map
    let quantity = (this.mapProductQuantity.get(product) || 0) - 1;
    if (quantity == 0)
      this.mapProductQuantity.delete(product);
    else
      this.mapProductQuantity.set(product, quantity);

    // Decrement total quantity and reduce unit price from total price
    this.totalQuantity--;
    this.totalPrice -= product.unitPrice;

    this.publishCartUpdated ();
  }

  /**
   * Remove all product entirely from cart. This is equivalent to quantity reduced to zero.
   */
  removeAll (product: Product)
  {
    if (!this.mapProductQuantity.has(product))
      throw new Error(`[CartService] Product ${product.name} does not exist`);

    let quantity = this.mapProductQuantity.get(product) || 0;
    this.mapProductQuantity.delete(product);
    this.totalQuantity -= quantity;
    this.totalPrice -= quantity * product.unitPrice;

    this.publishCartUpdated ();
  }

  publishCartUpdated ()
  {
    this.subjectCartUpdate.next();
  }
}
