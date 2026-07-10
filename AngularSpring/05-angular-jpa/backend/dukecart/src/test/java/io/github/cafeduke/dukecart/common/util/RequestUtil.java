package io.github.cafeduke.dukecart.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import org.testng.Assert;

import com.github.cafeduke.jget.HttpStatus;
import com.github.cafeduke.jget.JGet;

import io.github.cafeduke.dukecart.common.TestCase;

import static io.github.cafeduke.dukecart.common.util.TestProperties.*;

/**
 * The class shall be instantiated and populated with test specific instance variables
 */
public class RequestUtil
{
    private JGet jget;

    private Class<? extends TestCase> testClass;

    @SuppressWarnings("unused")
    private Logger logger;

    public RequestUtil(JGet jget, Logger logger, Class<? extends TestCase> testClass)
    {
        this.jget = jget;
        this.testClass = testClass;
        this.logger = logger;
    }

    public void assertAuthRequestOK(JGet.ArgBuilder builder, String token) throws IOException
    {
        assertAuthRequest(builder, token, HttpStatus.SC_OK);
    }

    public void assertAuthRequest(JGet.ArgBuilder builder, String token, int expectedRespCode) throws IOException
    {
        if (token != null)
            updateBuilderWithAuth(builder, token);
        Assert.assertEquals(jget.sendRequest(builder.build())[0], expectedRespCode);
    }

    /**
     * Update <b>builder</b> with
     * - Request headers like 'Content-Type'
     * - Request header file (if not already present) having Authorization header
     *
     * @param builder ArgBuilder
     * @param token Autorization token
     * @throws IOException
     */
    public void updateBuilderWithAuth(JGet.ArgBuilder builder, String token) throws IOException
    {
        File fileRQH = new File(getTestPrefix("rqh.txt"));
        if (!fileRQH.exists())
            Files.writeString(fileRQH.toPath(), "Authorization: Bearer %s".formatted(token));

        builder.header("Content-Type: application/json")
            .headerFile(fileRQH.getName());
    }

    /**
     * @param prefix Additional prefixes to be appended
     * @return Name derived using <b>prefix</b> and {@link #testClass}
     */
    public String getTestPrefix(String... prefix)
    {
        return TestUtil.getTestPrefix(testClass, prefix);
    }

    /**
     * ---------------------------------------------------------------------------------------------------
     * Keycloak related functions
     * ---------------------------------------------------------------------------------------------------
     */

    /**
     * Get Keycloak access token
     *
     * <pre>
     * jget
     *  -u "http://${KEYCLOAK_HOST}/realms/${KEYCLOAK_REALM_NAME}/protocol/openid-connect/token"
     *  -hdr "Content-Type: application/x-www-form-urlencoded"
     *  -P -pb "grant_type=password&client_id=${KEYCLOAK_CLIENT_ID}&username=${PROFILE_USERNAME}&password=${PASSWORD}"
     *  -o ${TEST_PREFIX}.token.out
     *  -rco ${TEST_PREFIX}.token.resp.out
     * </pre>
     */
    public String getKeycloakAccessToken(String username) throws IOException
    {
        String outBody = getTestPrefix() + ".json";

        JGet.ArgBuilder builder = JGet.newBuilder()
            .url(KEYCLOAK_TOKEN_URL)
            .header(CONTENT_TYPE_URLENCODED_HEADER)
            .doPost()
            .postBody(getKeycloakPostBody(username))
            .outputToFile(outBody);

        int respCode = jget.sendRequest(builder.build())[0];
        Assert.assertEquals(respCode, HttpStatus.SC_OK);

        return TestUtil.getJSONValue(outBody, "/access_token");
    }

    /**
     * @return The keyclock post body content for user
     */
    public String getKeycloakPostBody(String username)
    {
        return "grant_type=password&client_id=%s&username=%s&password=%s".formatted(KEYCLOAK_CLIENT_ID, username, PASSWORD);
    }

}
