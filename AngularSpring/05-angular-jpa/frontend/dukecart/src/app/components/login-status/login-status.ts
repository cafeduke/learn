import { Component, inject } from '@angular/core';
import KeycloakService from 'keycloak-js';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-login-status',
  imports: [],
  templateUrl: './login-status.html',
  styleUrl: './login-status.css'
})
export class LoginStatus
{
  // Dependency injection
  // --------------------
  private readonly keycloakService = inject(KeycloakService);
  private cartService = inject(CartService);

  doLogin()
  {
    this.keycloakService.login({ redirectUri: "http://localhost:8080/dukecart" });
  }

  doLogout()
  {
    this.cartService.emptyCart();
    this.keycloakService.logout({ redirectUri: "http://localhost:8080/dukecart" });
  }
}
