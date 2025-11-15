import { Component, inject } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { KEYCLOAK_AUTH_CONFIG } from '../../auth.config';

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
  // private authService = inject(OAuthService);

  constructor (private authService: OAuthService)
  {
    this.configure();
  }

  private configure ()
  {
    // this.authService.configure(KEYCLOAK_AUTH_CONFIG);

    // // Make a call to the issuer URI (KEYCLOAK_AUTH_CONFIG > issuer) and get all necessary endpoints required to trigger the login flow
    // this.authService.loadDiscoveryDocumentAndTryLogin();
  }

  doLogin ()
  {
    // this.authService.initCodeFlow();
  }

  doLogout ()
  {
    // this.authService.logOut();
  }
}
