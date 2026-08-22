import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SalesPersonList } from './sales-person-list/sales-person-list';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, SalesPersonList],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('02-sales');
}
