package com.oracle.ohsqa;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.KeyStore;
import java.time.Duration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

public class H2Client
{
    public static void main(String[] args) throws Exception
    {
        HttpClient.Builder builder = HttpClient.newBuilder()
            .version(Version.HTTP_1_1)
            .followRedirects(Redirect.NEVER)
            .proxy(HttpClient.Builder.NO_PROXY);

        System.setProperty("javax.net.ssl.keyStore", "/home/raghu/work/JGet.jks");
        System.setProperty("javax.net.ssl.keyStoreType", "JKS");
        System.setProperty("javax.net.ssl.keyStorePassword", "welcome1");

        /* Set trust store properties */
        System.setProperty("javax.net.ssl.trustStore", "/home/raghu/work/JGet.jks");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStorePassword", "welcome1");

        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");

        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream in = new java.io.FileInputStream("/home/raghu/work/JGet.jks");
        ks.load(in, "welcome1".toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        SSLParameters params = context.getDefaultSSLParameters();
        params.setProtocols(new String[] { "TLSv1.2", "TLSv1.1" });

        builder.sslParameters(params);

        HttpClient client = builder.connectTimeout(Duration.ofSeconds(20))
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .version(Version.HTTP_1_1)
            .uri(new URI("https://localhost:17701/DukeApp/Snoop.jsp"))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.out.println(response.version() + " " + response.statusCode());
        System.out.println();
        System.out.println(response.body());
    }
}
