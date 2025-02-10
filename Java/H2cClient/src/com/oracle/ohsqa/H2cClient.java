package com.oracle.ohsqa;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class H2cClient
{
    public static void main(String[] arg) throws Exception
    {
        if (arg.length != 3)
        {
            System.out.println("Usage: java -jar H2cClient.jar <1.1|2> <GET|POST> <url>");
            System.exit(1);
        }

        HttpClient.Version version = arg[0].equals("2") ? HttpClient.Version.HTTP_2 : HttpClient.Version.HTTP_1_1;

        String method = arg[1].equalsIgnoreCase("post") ? "POST" : "GET";

        URI uri = new URI(arg[2]);

        HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .version(version)
            .build();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .version(version)
            .uri(uri)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Accept", "*/*");

        if (method.equals("GET"))
            requestBuilder.GET();
        else
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString("greet=hello"));

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        System.out.println(response.version() + " " + response.statusCode());
        System.out.println();
        System.out.println(response.body());
    }
}
