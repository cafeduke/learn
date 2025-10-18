import { Component, signal } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { ProductList } from './components/product-list/product-list';
import { CategoryList } from './components/category-list/category-list';
import { ProductDetails } from './components/product-details/product-details';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, ProductList, ProductDetails, CategoryList],
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: true,
})
export class App {
  protected readonly title = signal('dukecart');

}
