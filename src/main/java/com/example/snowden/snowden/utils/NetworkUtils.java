package com.example.snowden.snowden.utils;

import java.io.IOException;

import com.example.snowden.snowden.Constants;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

public class NetworkUtils {
    // add this to constants.java file

    public static Document getResponse(String url) {
        Response res = null;
        Document document = null;
        try {
            res = Jsoup.connect(url).execute();
            if (res.statusCode() == 200) {
                String body = res.body();
                document = Jsoup.parseBodyFragment(body);
            }  else {
                Logger.log(url+" not working");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static Document getResponseForSubject(String subject) {
        String url = Constants.BASE_URL+ subject;
        //Logger.log(url);
        return getResponse(url);
    }
}
