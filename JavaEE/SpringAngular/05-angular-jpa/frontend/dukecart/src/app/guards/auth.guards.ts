import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, NavigationExtras, Router, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "../services/auth.service";

export const AuthGuard: CanActivateFn = async (route, state) =>
{
  // Dependency injection
  // --------------------
  RouterStateSnapshot
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.hasLoggedIn)
    authService.login();

  const requiredRoles = route.data["roles"] as Array<string>;
  const hasRequiredRole = authService.isUserAuthorized(requiredRoles);

  if (!hasRequiredRole)
  {
    const navigationExtras: NavigationExtras =
    {
      state:
      {
        errorSummary: "Unauthorized access by user " + authService.username,
        errorDetail: "User " + authService.username + " is not authorized to access URL " + state.url,
        responseCode: 401,
        stateURL: state.url,
        username: authService.username,
        requiredRoles: requiredRoles
      }
    };

    router.navigate(["/error"], navigationExtras);
    return false;
  }

  return true;
};
