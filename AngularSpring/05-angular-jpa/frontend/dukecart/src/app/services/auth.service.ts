import { effect, inject, Injectable, signal } from '@angular/core';
import { KEYCLOAK_EVENT_SIGNAL, KeycloakEventType } from 'keycloak-angular';
import KeycloakService from 'keycloak-js';
import Keycloak from 'keycloak-js';
import { DUKE_HOME } from '../app.const';

@Injectable({ providedIn: 'root' })
export class AuthService
{
  // Dependency injection
  // --------------------
  private readonly keycloakService = inject(KeycloakService);
  private readonly keycloakEventSignal = inject(KEYCLOAK_EVENT_SIGNAL);

  // Instance variable
  private authSignal = signal(false);

  constructor ()
  {
    console.log(`[AuthService] constructor Auth=${this.authSignal()}`);
    // Effect: An operation that runs whenever one or more signal values change
    // See: https://www.npmjs.com/package/keycloak-angular
    effect(async () =>
    {
      const keycloakEvent = this.keycloakEventSignal();
      switch (keycloakEvent.type)
      {
        case KeycloakEventType.Ready:
          this.authSignal.set(this.keycloakService.authenticated);
          break;

        case KeycloakEventType.AuthSuccess:
        case KeycloakEventType.AuthRefreshSuccess:
          this.authSignal.set(true);
          break;

        case KeycloakEventType.AuthLogout:
        case KeycloakEventType.AuthRefreshError:
        case KeycloakEventType.AuthError:
          this.authSignal.set(false);
          break;
      }
      console.log(`[AuthService] effect KeycloakEvent=${KeycloakEventType[keycloakEvent.type]} Auth=${this.authSignal()}`);
    });
  }

  hasLoggedIn (): boolean
  {
    return this.authSignal();
  }

  login(): void
  {
    this.keycloakService.login({ redirectUri: DUKE_HOME });
  }

  logout(): void
  {
    this.keycloakService.logout({ redirectUri: DUKE_HOME });
  }
}
