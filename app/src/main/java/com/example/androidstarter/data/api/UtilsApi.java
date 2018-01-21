package com.example.androidstarter.data.api;

/**
 * Created by samvedana on 17/1/18.
 */

public class UtilsApi {

    public static final String BASE_URL_API = "/"; //todo this will do nothing for now

    public static ZestService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ZestService.class);
    }
}
