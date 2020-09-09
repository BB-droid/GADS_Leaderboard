package com.bbapps.gadsleaderboard.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HoursService {

    public static final String BASE_URL = "https://gadsapi.herokuapp.com";
    private static final String FORM_BASE_URL = "https://docs.google.com/forms/d/e/";
    public static Retrofit retrofit;

    public static Retrofit getHoursInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getFormUrl() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(FORM_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
