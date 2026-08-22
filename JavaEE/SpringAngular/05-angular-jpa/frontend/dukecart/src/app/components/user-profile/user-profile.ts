import { Component, effect, inject, OnInit, signal } from '@angular/core';
import KeycloakService from 'keycloak-js';
import { KEYCLOAK_EVENT_SIGNAL, KeycloakEventType} from 'keycloak-angular';
import { CartService } from '../../services/cart.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { TitleCasePipe } from '@angular/common';

@Component({
  selector: 'app-user-profile',
  imports: [RouterLink, TitleCasePipe],
  templateUrl: './user-profile.html',
  styleUrl: './user-profile.css'
})
export class UserProfile  implements OnInit
{
  // Dependency injection
  // --------------------
  private readonly authService = inject(AuthService);
  private readonly cartService = inject(CartService);
  private readonly route = inject(ActivatedRoute);

  // Instance variables
  // ------------------
  hasLoggedIn:boolean = this.authService.hasLoggedIn;
  username:string = this.authService.username;

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

    // If routerPathSegment was 'login' or 'logout' the corresponding actions do not return back.
    // Instead the entire component gets reloaded. During the reload the routerPathSegment=undefined
    this.refreshProfile();
    this.authService.subjectAuthUpdate.subscribe(data =>
    {
      console.log("[UserProfile] subscriber notified by AuthService");
      this.refreshProfile();
    });
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

  private refreshProfile ()
  {
    this.hasLoggedIn = this.authService.hasLoggedIn;
    this.username = this.authService.username;
    console.log(`[UserProfile] Refreshed hasLoggedIn=${this.hasLoggedIn} username=${this.username}`);
  }
}
