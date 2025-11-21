import { Component, inject, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CategoryList } from './components/category-list/category-list';
import { Search } from './components/search/search';
import { CartStatus } from './components/cart-status/cart-status';
import { LoginStatus } from './components/login-status/login-status';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CategoryList, Search, CartStatus, LoginStatus],
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: true,
})
export class App
{
  protected readonly title = signal('dukecart');
}
