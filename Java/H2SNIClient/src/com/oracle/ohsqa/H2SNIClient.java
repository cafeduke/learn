package com.oracle.ohsqa;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.KeyStore;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

public class H2SNIClient
{
    public static void main(String arg[]) throws Exception
    {

        if (arg.length != 3)
        {
            System.out.println("java -jar H2SNIClient.jar <1.1|2> <sni-host> <url>");
            System.exit(1);
        }

        /* Parse arguments */

        HttpClient.Version version = arg[0].equals("2") ? HttpClient.Version.HTTP_2 : HttpClient.Version.HTTP_1_1;

        SNIHostName sniHost = new SNIHostName(arg[1]);

        URI uri = new URI(arg[2]);

        /* Set trust store properties */
        System.setProperty("jdk.httpclient.allowRestrictedHeaders", "host");

        System.setProperty("javax.net.ssl.keyStore", "JGet.jks");
        System.setProperty("javax.net.ssl.keyStoreType", "JKS");
        System.setProperty("javax.net.ssl.keyStorePassword", "welcome1");

        System.setProperty("javax.net.ssl.trustStore", "JGet.jks");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStorePassword", "welcome1");

        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");

        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream in = new java.io.FileInputStream("JGet.jks");
        ks.load(in, "welcome1".toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        /* Set SSL parameters */

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        SSLParameters params = context.getDefaultSSLParameters();
        params.setProtocols(new String[] { "TLSv1.3", "TLSv1.2" });

        List<SNIServerName> listSNI = new ArrayList<>();
        listSNI.add(sniHost);
        params.setServerNames(listSNI);

        /* Build HTTP client */
        HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .version(version)
            .followRedirects(Redirect.NEVER)
            .proxy(HttpClient.Builder.NO_PROXY)
            .sslParameters(params)
            .build();

        /* Request and response */
        HttpRequest request = HttpRequest.newBuilder()
            .version(version)
            .setHeader("Host", sniHost.getAsciiName())
            .setHeader("Content-Type", "application/x-www-form-urlencoded")
            .setHeader("Accept", "*/*")
            .uri(uri)
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.out.println("Response Status");
        System.out.println("---------------");
        System.out.println(response.version() + " " + response.statusCode());
        System.out.println("");

        System.out.println("Response Body");
        System.out.println("-------------");
        System.out.println(response.body());
    }
}
