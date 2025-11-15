import { AuthConfig } from "angular-oauth2-oidc";

export const KEYCLOAK_AUTH_CONFIG: AuthConfig =
{
  // Configuration endpoints exposed by the authorization server. Client obtains all end-points using issuer URL as starting point
  // Keycloak admin console > (Select Realm) > Realm settings > Endpoints > OpenID Endpoint Configuration
  issuer:"http://localhost:8181/realms/duke-realm",

  // The URI to redirect to after successful login.
  // The window.location.origin points to the initial web-app request (original request, before it was redirected for login)
  redirectUri: window.location.origin,

  // The clientId as created in keycloak
  // Keycloak admin console > (Select Realm) > Clients > (Select ClientId)
  clientId: "angular-dukecart-client",

  // Authorization code flow
  responseType: "code",

  // specific to angular-oauth2-oidc library
  // If true all URLs in OIDC config originate from the same host (See Keycloak admin console > (Select Realm) > Realm settings > Endpoints > OpenID Endpoint Configuration)
  strictDiscoveryDocumentValidation: true,

  // Default scopes
  scope: "openid profile email offline_access"
};
