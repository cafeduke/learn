import { Component, inject, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CategoryList } from './components/category-list/category-list';
import { Search } from './components/search/search';
import { CartStatus } from './components/cart-status/cart-status';
import { UserAction } from './components/user-action/user-action';
import { AuthService } from './services/auth.service';
import { DUKE_APP_NAME } from './app.const';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CategoryList, Search, CartStatus, UserAction],
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: true,
})
export class App
{
  // Dependency injection
  // --------------------
  protected readonly authService = inject(AuthService);

  protected readonly title = signal(DUKE_APP_NAME);
}
