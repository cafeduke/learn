import { computed, effect, inject, Injectable, signal } from '@angular/core';
import { KEYCLOAK_EVENT_SIGNAL, KeycloakEventType } from 'keycloak-angular';
import KeycloakService, { KeycloakProfile } from 'keycloak-js';
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
  hasLoggedIn: boolean = false;
  username: string = "Guest";
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

  /**
   * Return true only if the current logged-in user has all the {requiredRoles}.
   * Fetch the roles (say userRoles) of the current logged-in user. Ensure {requiredRoles} is a subset of userRoles
   */
  isUserAuthorized(requiredRoles: string[]): boolean
  {
    if (!this.hasLoggedIn || !requiredRoles || requiredRoles.length == 0)
      return false;

    /**
     * Check roles using the base library function
     *  - We are iterating through the requiredRoles. The current logged in user (or just user) MUST have all these roles.
     *  - keycloakService.hasRealmRole(role)                             : Check if the 'user' has REALM 'role' (REALM roles are applicable across applications)
     *  - keycloakService.hasResourceRole(role, keycloakService.clientId): Check if 'user' has client role for current clientId
     */
    return requiredRoles.some(role => this.keycloakService.hasRealmRole(role) || this.keycloakService.hasResourceRole(role, this.keycloakService.clientId))
  }

}
