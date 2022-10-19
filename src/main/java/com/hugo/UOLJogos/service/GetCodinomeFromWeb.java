package com.hugo.UOLJogos.service;

import com.hugo.UOLJogos.model.enums.ContentType;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetCodinomeFromWeb {

    private static final String URL_API_JSON
            = "https://raw.githubusercontent.com/0MiniUgo/test-backEnd-Java/master/referencias/vingadores.json";
    private static final String URL_API_XML
            = "https://raw.githubusercontent.com/0MiniUgo/test-backEnd-Java/master/referencias/liga_da_justica.xml";

    private static final String REGEX_JSON = "\"codinome\": \"([^\"]*)\"";
    private static final String REGEX_XML = "<codinome>([^\"]*?)</codinome>";



    public static String WebToString(ContentType type){

        String url = "";

        if(type == ContentType.JSON){
            url = URL_API_JSON;

        } else if (type == ContentType.XML) {
            url = URL_API_XML;
        }

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(url))
                .header("accept", "application/" + type.getDescricao())
                .build();

        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        return response;
    }

    public static List<String> StringToList(ContentType type){

        String str = WebToString(type);

        String pattern = "";
        if(type == ContentType.JSON){
            pattern = REGEX_JSON;

        } else if (type == ContentType.XML) {
            pattern = REGEX_XML;
        }

        List<String> list = new ArrayList<>();

        Matcher matcher = Pattern.compile(pattern).matcher(str);

        while(matcher.find()){
            for(int i = 1; i <= matcher.groupCount(); i++){
                list.add(matcher.group(i));
            }
        }

        return list;
    }
}
