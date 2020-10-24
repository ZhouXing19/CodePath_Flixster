package com.example.myinstagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("rIGVkCa2kGikARND7hj2EsiNlBGf6ZXIWdjiaCm1")
                .clientKey("9ZQ5aqRsDaUzQoJS8Fk8IAkhEgyFI4xcVpmDU4EA")
                .server("https://parseapi.back4app.com")
                .build());
    }
}
