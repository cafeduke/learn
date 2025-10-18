import { Component, OnInit, inject } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../model/product';

@Component({
  selector: 'app-product-details',
  imports: [],
  templateUrl: './product-details.html',
  styleUrl: './product-details.css'
})
export class ProductDetails implements OnInit
{
  // Dependency injection
  private productService = inject(ProductService);
  private route = inject(ActivatedRoute);

  // Instance variables
  productId: number = 1;

  product!: Product;

  constructor () { }

  ngOnInit(): void
  {
    this.route.paramMap.subscribe(params => {
      this.productId = (this.route.snapshot.paramMap.has('productId')) ? Number(this.route.snapshot.paramMap.get('productId')) : 1;
      console.log("RBSESHAD: ProductDetails: productId=" + this.productId);
      this.refreshProduct();
    });
  }

  refreshProduct(): void
  {
    // See route.ts. The categoryId is configured as a param. Retrieve it. The default value is 1
    this.productService
      .getProduct(this.productId)
      .subscribe(data =>
      {
        this.product = data;
        console.log("RBSESHAD: ProductDetails: productName=" + this.product.name);
      });
  }
}
