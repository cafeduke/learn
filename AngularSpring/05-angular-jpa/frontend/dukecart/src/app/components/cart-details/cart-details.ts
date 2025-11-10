import { Component, inject, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { Product } from '../../model/product';
import { CurrencyPipe} from '@angular/common';
import { RouterLink } from '@angular/router';
import { CartItem } from '../../model/cart-item';

@Component({
  selector: 'app-cart-details',
  imports: [RouterLink, CurrencyPipe],
  templateUrl: './cart-details.html',
  styleUrl: './cart-details.css'
})
export class CartDetails implements OnInit
{
  // Instance variables
  // ------------------
  listCartItem: CartItem[] = [];
  totalPrice: number = 0.0;
  totalQuantity: number = 0;

  // Dependency injection
  // --------------------
  private cartService = inject(CartService);

  constructor ()
  {
  }

  ngOnInit(): void
  {
    this.refreshCart();
    this.cartService.subjectCartUpdate.subscribe(data => {
      console.log("[CartDetails] subscriber notified by CartService");
      this.refreshCart();
    });
  }

  doCalculateSubTotal (item: CartItem)
  {
    return item.unitPrice * item.quantity;
  }

  doAddToCart (item: CartItem)
  {
    this.cartService.addToCart(item);
  }

  doRemoveFromCart (item: CartItem)
  {
    this.cartService.removeFromCart(item);
  }

  doRemoveAll(item: CartItem)
  {
    this.cartService.removeAll(item);
  }

  doCheckout()
  {

  }

  private refreshCart (): void
  {
    this.listCartItem = this.cartService.listCartItem;
    this.totalPrice = this.cartService.totalPrice;
    this.totalQuantity = this.cartService.totalQuantity;
  }
}
