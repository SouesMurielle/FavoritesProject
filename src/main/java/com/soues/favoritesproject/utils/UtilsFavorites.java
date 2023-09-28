package com.soues.favoritesproject.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UtilsFavorites {

    public static boolean CheckValidityURL(String lien) {
            boolean response = false;
            try {
                URL url = new URL (lien) ;
                HttpURLConnection connection = (HttpURLConnection) url.openConnection ();
                connection.setRequestMethod ("GET") ;
                if (connection.getResponseCode () == HttpURLConnection.HTTP_OK) {
                    response = true;
                    System.out.println (lien + " --> Status == TRUE");
                }
            } catch (IOException e) {
                System.out.println (lien + " --> Status == FALSE");
            }
            return response;
    }
}
