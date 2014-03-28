package com.bignerdranch.android.criminalintent.yelpapi;

import android.util.Log;

import com.google.gson.*;
//import com.yelp.v2.Business;
//import com.yelp.v2.YelpSearchResult;
import v2.Business;
import v2.YelpSearchResult;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class APISearch {

    // Define your keys, tokens and secrets.  These are available from the Yelp website.
    private static final String CONSUMER_KEY = "RyZXgFghTEKhwmCp5sH6dA";
    private static final String CONSUMER_SECRET = "aUpRy0DQ6o5l1JEWQ8bXwHK-tcQ";
    private static final String TOKEN = "u7qCZDKy-d1oppJts-yEUZyE769K7gxa";
    private static final String TOKEN_SECRET = "Ol5Lp4AfnpgE_ORr8Tr7f4V7Zro";

    // Some example values to pass into the Yelp search service.
    //    String lat = "30.361471";
    //    String lng = "-87.164326";
    //    String city = "Chicago";
    //    String restaurant = "Girl & The Goat";

    String category = "restaurants";
    String searchResultLimit = "1";
    String resName, resAddress, resPhone, resLatLong, resYelp;
    double lat, lon;

    private YelpSearchResult places;

    public YelpSearchResult search(String restaurant, String city) {

        // Execute a signed call to the Yelp service.
        OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
        Token accessToken = new Token(TOKEN, TOKEN_SECRET);
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");

        request.addQuerystringParameter("location", city);
        request.addQuerystringParameter("category", category);
        request.addQuerystringParameter("term", restaurant);
        request.addQuerystringParameter("limit", searchResultLimit);

        service.signRequest(accessToken, request);
        Response response = request.send();
        String rawData = response.getBody();

        try {
            places = new Gson().fromJson(rawData, YelpSearchResult.class);

            for (Business biz : places.getBusinesses()) {
                resName = biz.getName();
                String phone = biz.getPhone();
                resPhone = "(" + phone.substring(0, 3) + ") " +
                        phone.substring(3, 6) + " - " +
                        phone.substring(6);



                for (String address : biz.getLocation().getAddress()) {
                    resAddress = address;
                }

                resYelp = biz.getUrl();






//                lat = biz.getLocation().getCoordinate().getLatitude();
//                Log.d("message", "THIS IS LAT" + lat);
//                lon = biz.getLocation().getCoordinate().getLongitude();
//
//                resLatLong =""+ lat + "," + lon;


                //resLatLong = "" + String.valueOf(biz.getLocation().getCoordinate().getLatitude())
                //        + String.valueOf(biz.getLocation().getCoordinate().getLongitude());


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return places;
    }

    public String getResName() {
        return resName;
    }

    public String getResAddress() {
        return resAddress;
    }

    public String getResPhone() {
        return resPhone;
    }

    public String getResLatLong() {
        return resLatLong;
    }

    public String getResYelp() {
        return resYelp;
    }

}
