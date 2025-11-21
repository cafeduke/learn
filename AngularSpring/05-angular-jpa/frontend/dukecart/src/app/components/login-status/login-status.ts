import { Component, inject } from '@angular/core';
import KeycloakService from 'keycloak-js';

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

  doLogin()
  {
    this.keycloakService.login({ redirectUri: "http://localhost:8080/dukecart" });
  }

  doLogout()
  {
    this.keycloakService.logout({ redirectUri: "http://localhost:8080/dukecart" });
  }
}
