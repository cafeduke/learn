package io.github.cafeduke.dukecart.controller;

import static io.github.cafeduke.dukecart.common.util.TestProperties.SPRING_BOOT_BASE_URI;

import java.io.IOException;

import org.testng.annotations.Test;

import com.github.cafeduke.jget.JGet;

import io.github.cafeduke.dukecart.common.TestCase;
import io.github.cafeduke.dukecart.common.util.TestUtil;
import io.github.cafeduke.dukecart.dto.CustomerProfileDTO;

public class CustomerProfileControllerTest extends TestCase
{
  public static final String CUSTOMER_PROFILE_BASE_URI = SPRING_BOOT_BASE_URI + "/customer-profile";

  public static final String CUSTOMER_PROFILE_READ_URI = CUSTOMER_PROFILE_BASE_URI + "/read";

  public static final String USER_DUKE_APPLE = "dukeapple";

  @Test
  public void readCustomerProfile() throws IOException
  {
    String token = reqUtil.getKeycloakAccessToken(USER_DUKE_APPLE);
    logger.info("Received token");

    String outJSON = reqUtil.getTestPrefix("read", USER_DUKE_APPLE, "json");
    JGet.ArgBuilder builder = JGet.newBuilder()
      .url(CUSTOMER_PROFILE_READ_URI + "/" + USER_DUKE_APPLE)
      .outputToFile(outJSON);
    reqUtil.assertAuthRequestOK(builder, token);
    TestUtil.writePrettyJSON(outJSON);
    TestUtil.logJSON(logger, outJSON);
  }

  @Test(dependsOnMethods = "readCustomerProfile")
  public void updateCustomerProfile() throws IOException
  {
    String outJSON = reqUtil.getTestPrefix("read", USER_DUKE_APPLE, "json");
    CustomerProfileDTO customerProfileDTO = TestUtil.convertJSONToObject(outJSON, CustomerProfileDTO.class);
    TestUtil.logObjectAsJSON(logger, customerProfileDTO);
  }
}
