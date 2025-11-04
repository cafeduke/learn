import { Component, inject, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { CurrencyPipe } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-cart-status',
  imports: [RouterLink, CurrencyPipe],
  templateUrl: './cart-status.html',
  styleUrl: './cart-status.css'
})
export class CartStatus implements OnInit
{
  // Dependency injection
  // --------------------
  private cartService = inject(CartService);

  // Instance Variables
  // ------------------
  totalPrice:number = 0.0;
  totalQuantity:number = 0;

  ngOnInit(): void
  {
    this.cartService.subjectCartUpdate.subscribe(data => {
      this.totalPrice = this.cartService.totalPrice;
      this.totalQuantity = this.cartService.totalQuantity;
      console.log("[CartStatus] subscriber notified by CartService totalPrice=" + this.totalPrice + " totalQuantity=" + this.totalQuantity);
    });
  }
}
