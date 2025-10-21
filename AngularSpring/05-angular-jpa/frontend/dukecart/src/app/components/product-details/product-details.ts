import { Component, OnInit, inject } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Product } from '../../model/product';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-product-details',
  imports: [CurrencyPipe, RouterLink],
  templateUrl: './product-details.html',
  styleUrl: './product-details.css'
})
export class ProductDetails implements OnInit
{
  // Dependency injection
  private productService = inject(ProductService);
  private route = inject(ActivatedRoute);

  // Instance variables
  id: number = 1;
  product!: Product;

  constructor () { }

  ngOnInit(): void
  {
    this.route.paramMap.subscribe(params => {
      this.id = (this.route.snapshot.paramMap.has('id')) ? Number(this.route.snapshot.paramMap.get('id')) : 1;
      this.refreshProduct();
    });
  }

  refreshProduct(): void
  {
    // See route.ts. The categoryId is configured as a param. Retrieve it. The default value is 1
    this.productService
      .getProductById(this.id)
      .subscribe (data => this.product = data);
  }
}
