package io.github.cafeduke.dukecart.common.util;

public class TestProperties
{
  public static final String PROJECT_NAME = "dukecart";
  
  public static final String LineSep = System.getProperty("line.separator"); 
  
  public static final String PACKAGE_PREFIX = "io.github.cafeduke.dukecart";
  
  public static final String PACKAGE_PREFIX_DOT = PACKAGE_PREFIX + ".";
  
  public static final String KEYCLOAK_HOST = "localhost:8181";
  
  public static final String KEYCLOAK_REALM_NAME = "duke-realm";
  
  public static final String KEYCLOAK_CLIENT_ID = "angular-dukecart-client";
  
  public static final String KEYCLOAK_TOKEN_URL = "http://%s/realms/%s/protocol/openid-connect/token".formatted(KEYCLOAK_HOST, KEYCLOAK_REALM_NAME); 
  
  public static final String PASSWORD = "welcome1";
  
  public static final String CONTENT_TYPE_URLENCODED_HEADER = "Content-Type: application/x-www-form-urlencoded";
  
  public static final String SPRING_BOOT_HOST = "localhost:9090";
  
  public static final String SPRING_BOOT_BASE_URI = "http://%s/%s".formatted(SPRING_BOOT_HOST, PROJECT_NAME);
}
