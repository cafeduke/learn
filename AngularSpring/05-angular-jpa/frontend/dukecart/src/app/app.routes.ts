import { Routes, RouterModule } from '@angular/router';
import { ProductList } from './components/product-list/product-list';
import { ProductDetails } from './components/product-details/product-details';

/**
 * An empty path means request to the site without any path prefix ('http://<hostname>/')
 * A path with '**' means any request that does not match the above
 */
export const routes: Routes =
[
  { 'path':'search/:searchKey', component:ProductList },
  { 'path':'products/findByCategoryId/:categoryId', component:ProductList },
  { 'path':'products/:id', component:ProductDetails },
  { 'path':'products', component:ProductList },
  { 'path':'', redirectTo:'/products', pathMatch:'full' },
  { 'path':'**', redirectTo:'/products', pathMatch:'full' }
];
