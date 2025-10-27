import { Component, OnInit, inject } from '@angular/core';
import { Product } from '../../model/product';
import { ProductService } from '../../services/product.service';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { NgbPagination } from '@ng-bootstrap/ng-bootstrap';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-product-list',
  imports: [CurrencyPipe,CommonModule, RouterLink, NgbPagination],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
  standalone: true
})
export class ProductList implements OnInit
{

  // Dependency injection
  // --------------------
  private route = inject(ActivatedRoute);
  private productService = inject(ProductService);
  private cartService = inject(CartService);

  // Instance variables
  // ------------------

  /**
   * The selected list of products based on query
   */
  listProduct: Product[] = [];

  /**
   * The category id to filter products
   */
  categoryId: number = 1;

  /**
   * The search key to filter products
   */
  searchKey: string|null = null;

  // Default pagenation properties
  // -----------------------------
  pageNumber:number = 1;
  pageSize:number = 8;
  totalSize:number = 0;

  constructor () { }

  /**
   * CallBack: Invoked once the component is initalized
   * The subscribe is invoked in async fashion, run in the background.
   * Once the data is available the product array is populated
   *
   * See route.ts.
   *  - The searchKey is configured as a param. Retrieve it. The default value is null
   *  - The categoryId is configured as a param. Retrieve it. The default value is 1
   */
  ngOnInit()
  {
    this.route.paramMap.subscribe(params =>
    {
      this.categoryId = (this.route.snapshot.paramMap.has('categoryId')) ? Number(this.route.snapshot.paramMap.get('categoryId')) : 1;
      this.searchKey  = (this.route.snapshot.paramMap.has('searchKey'))  ? this.route.snapshot.paramMap.get('searchKey')          : null;
      this.pageNumber = 1;
      this.refreshProductList ();
    });
  }

  refreshProductList (): void
  {
    let observableJSON = (this.searchKey == null) ? this.productService.getProductsByCategoryIdPaginate(this.categoryId, this.pageNumber - 1, this.pageSize)
                                                  : this.productService.searchProductsPaginate(this.searchKey, this.pageNumber - 1, this.pageSize);
    observableJSON.subscribe(data =>
    {
      this.listProduct = data._embedded.products
      this.pageNumber = data.page.number + 1;
      this.pageSize = data.page.size;
      this.totalSize = data.page.totalElements;
    });
  }

  doUpdatePageSize(pageSize: string)
  {
    this.pageSize = Number(pageSize);
    this.pageNumber = 1;
    this.refreshProductList ();
  }

  doAddToCart(product: Product): void
  {
    this.cartService.addToCart(product);
  }
}
