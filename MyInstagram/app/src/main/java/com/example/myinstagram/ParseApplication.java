package com.example.myinstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Register yout parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("rIGVkCa2kGikARND7hj2EsiNlBGf6ZXIWdjiaCm1")
                .clientKey("9ZQ5aqRsDaUzQoJS8Fk8IAkhEgyFI4xcVpmDU4EA")
                .server("https://parseapi.back4app.com")
                .build());
    }
}
