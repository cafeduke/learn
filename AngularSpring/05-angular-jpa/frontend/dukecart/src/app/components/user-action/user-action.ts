import { Component, effect, inject, OnInit, signal } from '@angular/core';
import KeycloakService from 'keycloak-js';
import { KEYCLOAK_EVENT_SIGNAL, KeycloakEventType} from 'keycloak-angular';
import { CartService } from '../../services/cart.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-user-action',
  imports: [RouterLink],
  templateUrl: './user-action.html',
  styleUrl: './user-action.css'
})
export class UserAction  implements OnInit
{
  // Dependency injection
  // --------------------
  private readonly authService = inject(AuthService);
  private readonly cartService = inject(CartService);
  private readonly route = inject(ActivatedRoute);

  // Instance variables
  // ------------------
  hasLoggedIn:boolean = false;

  constructor () { }

  ngOnInit()
  {
    const routerPathSegment = this.route.snapshot.url[0]?.path;
    console.log("[UserAction] ngOnInit routerPathSegment=" + routerPathSegment);
    if (routerPathSegment)
    {
      if (routerPathSegment == "login")
        this.doLogin();
      else if (routerPathSegment == "logout")
        this.doLogout();
    }

    // If we are here neither login nor logout was called.
    // It could just be a component reload before/after login/logout
    this.hasLoggedIn = this.authService.hasLoggedIn();
    console.log(`[UserAction] ngOnInit routerPathSegment=${routerPathSegment} hasLoggedIn=${this.hasLoggedIn}`);
  }

  doLogin(): void
  {
    this.authService.login();
  }

  doLogout(): void
  {
    this.cartService.emptyCart();
    this.authService.logout();
  }
}
