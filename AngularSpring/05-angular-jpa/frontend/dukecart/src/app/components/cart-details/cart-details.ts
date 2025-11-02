import { Component, inject, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { Product } from '../../model/product';
import { CurrencyPipe, KeyValuePipe } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-cart-details',
  imports: [KeyValuePipe, RouterLink, CurrencyPipe],
  templateUrl: './cart-details.html',
  styleUrl: './cart-details.css'
})
export class CartDetails implements OnInit
{
  // Instance variables
  // ------------------
  mapProductQuantity: Map<Product,number> ;
  totalPrice: number = 0.0;
  totalQuantity: number = 0;

  // Dependency injection
  // --------------------
  private cartService = inject(CartService);

  constructor ()
  {
    this.mapProductQuantity = new Map<Product, number>();
  }

  ngOnInit(): void
  {
    this.refreshCart();
    this.cartService.subjectCartUpdate.subscribe(data => {
      console.log("[CartDetails] subscriber notified");
      this.refreshCart();
    });
  }

  doCalculateSubTotal (product: Product)
  {
    return product.unitPrice * (this.mapProductQuantity.get(product) || 0);
  }

  doAddToCart (product: Product)
  {
    this.cartService.addToCart(product);
  }

  doRemoveFromCart (product: Product)
  {
    this.cartService.removeFromCart(product);
  }

  doRemoveAll(product: Product)
  {
    this.cartService.removeAll(product);
  }

  doCheckout()
  {

  }

  private refreshCart (): void
  {
    this.mapProductQuantity = this.cartService.mapProductQuantity;
    this.totalPrice = this.cartService.totalPrice;
    this.totalQuantity = this.cartService.totalQuantity;
  }
}
