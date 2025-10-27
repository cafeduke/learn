import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CategoryList } from './components/category-list/category-list';
import { Search } from './components/search/search';
import { CartStatus } from './components/cart-status/cart-status';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CategoryList, Search, CartStatus],
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: true,
})
export class App {
  protected readonly title = signal('dukecart');

}
