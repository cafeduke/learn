import { Component, OnInit, inject } from '@angular/core';
import { Product } from '../../model/product';
import { ProductService } from '../../services/product.service';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-product-list',
  imports: [CurrencyPipe,CommonModule, RouterLink],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
  standalone: true
})
export class ProductList implements OnInit
{
  // Dependency injection
  private productService = inject(ProductService);
  private route = inject(ActivatedRoute);

  // Instance variables
  listProduct: Product[] = [];

  categoryId: number = 1;

  constructor () { }

  /**
   * CallBack: Invoked once the component is initalized
   * The subscribe is invoked in async fashion, run in the background.
   * Once the data is available the product array is populated
   */
  ngOnInit()
  {
    this.route.paramMap.subscribe(params => {
      this.categoryId = (this.route.snapshot.paramMap.has('categoryId')) ? Number(this.route.snapshot.paramMap.get('categoryId')) : 1;
      this.refreshProductList ();
    });
  }

  refreshProductList (): void
  {
    // See route.ts. The categoryId is configured as a param. Retrieve it. The default value is 1
    this.productService
      .getProducts(this.categoryId)
      .subscribe(data => {this.listProduct = data});
  }
}
