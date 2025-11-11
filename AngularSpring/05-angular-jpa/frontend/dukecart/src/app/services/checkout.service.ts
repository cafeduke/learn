import { HttpClient } from '@angular/common/http';
import { inject, Injectable} from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { Purchase } from '../model/purchase';
import { CartItem } from '../model/cart-item';
import { OrderItem } from '../model/order-item';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService
{
  private static readonly BASE_URL = "http://localhost:9090/dukecart/checkout";

  // Dependency injection
  private httpClient = inject(HttpClient);

  constructor () { }

  placeOrder (purchase: Purchase): Observable<any>
  {
    return this.httpClient.post<Purchase>(CheckoutService.BASE_URL + "/purchase", purchase);
  }

  convertCartItemToOrderItem (cartItem: CartItem) :OrderItem
  {
    const orderItem:OrderItem = {
      productId: cartItem.productId,
      unitPrice: cartItem.unitPrice,
      quantity: cartItem.quantity,
      imageUrl: cartItem.imageUrl
    };
    return orderItem;
  }
}
