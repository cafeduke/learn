import { Injectable} from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { Product } from '../model/product';
import { CartItem } from '../model/cart-item';

@Injectable({
  providedIn: 'root'
})
export class CartService
{
  // Instance variables
  // ------------------

  /**
   * NOTE: Need for Item
   * -------------------
   * We are using an item array rather than mapProductQuantity (map of product to quantity) for the following reason.
   *  - Consider product b1=(BookA, 10$) and b2=(BookB, 20$)
   *  - Consider an order o1=(b1, b1, b2, 40$) stored in database as order_item1=(o1, b1, 2)  order_item2=(o1, b2, 1)
   *  - Total price in o1 is 40 == 2 * 10 + 20 (makes sense)
   *
   * Now, consider that the BookA price later changes to 15$. If order o1 is fetched now
   *  - it will show 2 BookA each 15$ and BookB 20$ adding up to 50 == 2*15+20. However, total price shows as 40 (as saved for o1 in database)
   *
   * Issue
   *  - The issue here is we should not reference the order_item with product-id (like b1, b2)
   *  - Instead the order_item must be as follows: order_item1=(o1, BookA, 10$, 2) order_item2=(o1, BookB, 20$, 1)
   */
  // mapProductQuantity: Map<Product,number>;
  listCartItem: CartItem[] = [];
  totalQuantity:number = 0;
  totalPrice:number = 0.0;

  // Subjects
  // --------
  // A subject calls its subscribers when an event occurs
  subjectCartUpdate: Subject<void> = new Subject<void>();

  constructor ()
  {
  }

  /**
   * Add the product to cart thus incrementing quantity and total price.
   */
  addToCart (cartItem: CartItem) :void
  {
    const searchIndex = this.listCartItem.findIndex(currItem => currItem.productId === cartItem.productId);
    const searchItem = (searchIndex == -1) ? cartItem : this.listCartItem[searchIndex];
    searchItem.quantity++;

    if (searchIndex == -1)
      this.listCartItem.push(searchItem);

    this.totalQuantity++;
    this.totalPrice += cartItem.unitPrice;
    this.publishCartUpdated ();
  }

  /**
   * Remove product from cart thus decremeting quantity and total price.
   */
  removeFromCart (cartItem: CartItem) :void
  {
    const searchIndex = this.listCartItem.findIndex(currItem => currItem.productId === cartItem.productId);
    if (searchIndex == -1)
      throw new Error(`[CartService] Product ${cartItem.name} does not exist`);

    const searchItem = this.listCartItem[searchIndex];
    if (searchItem.quantity == 1)
      this.listCartItem.splice(searchIndex, 1);
    else
      searchItem.quantity--;

    // Decrement total quantity and reduce unit price from total price
    this.totalQuantity--;
    this.totalPrice -= cartItem.unitPrice;

    this.publishCartUpdated ();
  }

  /**
   * Remove all product entirely from cart. This is equivalent to quantity reduced to zero.
   */
  removeAll (cartItem: CartItem) :void
  {
    const searchIndex = this.listCartItem.findIndex(currItem => currItem.productId === cartItem.productId);
    if (searchIndex == -1)
      throw new Error(`[CartService] Product ${cartItem.name} does not exist`);

    // Remove item from list
    const searchItem = this.listCartItem[searchIndex];
    this.listCartItem.splice(searchIndex, 1);

    // Update total
    this.totalQuantity -= searchItem.quantity;
    this.totalPrice -= searchItem.quantity * searchItem.unitPrice;

    this.publishCartUpdated ();
  }

  emptyCart () :void
  {
    this.listCartItem = [];
    this.totalQuantity = 0;
    this.totalPrice = 0.0;
    this.publishCartUpdated ();
  }

  publishCartUpdated () :void
  {
    this.subjectCartUpdate.next();
  }

  convertProductToCartItem (product: Product) :CartItem
  {
    const cartItem: CartItem =
    {
      productId: product.id,
      name: product.name,
      imageUrl: product.imageUrl,
      unitPrice: product.unitPrice,
      quantity: 0
    };
    return cartItem;
  }
}
