package com.hugo.UOLJogos.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LigaDaJusticaFromXML {

    private static final String URL_API
            = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml";

    public static String LigaDaJustica_XmlToString(){

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(URL_API))
                .header("accept", "application/xml")
                .build();

        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        return response;
    }

    public static void LigaDaJustica_StringToList(){

        String str = LigaDaJustica_XmlToString();

        List<String> list = new ArrayList<>();

        Matcher matcher = Pattern.compile("<codinome>([^\"]*?)</codinome>").matcher(str);

        while(matcher.find()){
            for(int i = 1; i <= matcher.groupCount(); i++){
                list.add(matcher.group(i));
            }
        }

        System.out.println(list);
    }
}
