import { computed, effect, inject, Injectable, signal } from '@angular/core';
import { KEYCLOAK_EVENT_SIGNAL, KeycloakEventType } from 'keycloak-angular';
import KeycloakService, { KeycloakProfile } from 'keycloak-js';
import Keycloak from 'keycloak-js';
import { DUKE_HOME } from '../app.const';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService
{
  // Dependency injection
  // --------------------
  private readonly keycloakService = inject(KeycloakService);
  private readonly keycloakEventSignal = inject(KEYCLOAK_EVENT_SIGNAL);

  // Private Instance variables
  // --------------------------
  private authenticationSignal = signal(false);

  // Public instance variables
  // --------------------------
  hasLoggedIn:boolean = false;
  username:string = "Guest";
  subjectAuthUpdate: Subject<void> = new Subject<void>();

  constructor ()
  {
    console.log(`[AuthService] constructor Auth=${this.authenticationSignal()}`);
    // Effect: An operation that runs whenever one or more signal values change
    // See: https://www.npmjs.com/package/keycloak-angular
    effect(async () =>
    {
      const keycloakEvent = this.keycloakEventSignal();
      switch (keycloakEvent.type)
      {
        case KeycloakEventType.Ready:
          this.authenticationSignal.set(this.keycloakService.authenticated);
          break;

        case KeycloakEventType.AuthSuccess:
        case KeycloakEventType.AuthRefreshSuccess:
          this.authenticationSignal.set(true);
          break;

        case KeycloakEventType.AuthLogout:
        case KeycloakEventType.AuthRefreshError:
        case KeycloakEventType.AuthError:
          this.authenticationSignal.set(false);
          break;
      }

      this.hasLoggedIn = this.authenticationSignal();

      const userProfile: KeycloakProfile = await this.keycloakService.loadUserProfile();
      this.username = userProfile.username ?? "Guest";
      console.log(`[AuthService] effect KeycloakEvent=${KeycloakEventType[keycloakEvent.type]} Auth=${this.hasLoggedIn} Username=${this.username}`);
      this.subjectAuthUpdate.next();
    });
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
