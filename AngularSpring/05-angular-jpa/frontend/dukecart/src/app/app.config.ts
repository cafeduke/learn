import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection, importProvidersFrom, inject, provideAppInitializer } from "@angular/core";
import { provideRouter } from "@angular/router";
import { provideHttpClient, withInterceptors } from "@angular/common/http";
import { routes } from "./app.routes";
import { createInterceptorCondition, INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG, IncludeBearerTokenCondition, includeBearerTokenInterceptor, provideKeycloak } from "keycloak-angular";

/**
 * Define the URL patterns where the bearer token should be included
 *  - Use a regex that matches your specific backend API URL(s)
 *  - Bearer prefix, defaults to "Bearer"
 */
const urlCondition1 = createInterceptorCondition<IncludeBearerTokenCondition> (
{
  urlPattern: /^(http:\/\/localhost:9090\/dukecart\/checkout\/purchase)(\/.*)?$/i,
  bearerPrefix: "Bearer"
});

const urlCondition2 = createInterceptorCondition<IncludeBearerTokenCondition> (
{
  urlPattern: /^http:\/\/localhost:9090\/dukecart\/products\/1$/i,
  bearerPrefix: "Bearer"
});


export const appConfig: ApplicationConfig =
{
  providers: [
    provideBrowserGlobalErrorListeners(),

    // Prepare the KeycloakService
    provideKeycloak (
    {
      config:
      {
        url: "http://localhost:8181",
        realm: "duke-realm",
        clientId: "angular-dukecart-client"
      },
      initOptions:
      {
        onLoad: undefined
        // onLoad: "check-sso",
        // silentCheckSsoRedirectUri: window.location.origin + "dukecart/silent-check-sso.html"
      }
    }),

    // Provide the config conditions for for the interceptor (includeBearerTokenInterceptor)
    {
      provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
      useValue: [urlCondition1, urlCondition2] // Pass your conditions as an array
    },

    provideZoneChangeDetection({ eventCoalescing: true }),

    provideRouter(routes),

    // Provide HttpClient and use the built-in interceptor
    provideHttpClient (withInterceptors([includeBearerTokenInterceptor]))

  ]
};
