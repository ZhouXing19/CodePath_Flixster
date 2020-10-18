package com.codepath.apps.restclienttemplate.models;

import androidx.room.Embedded;

import java.util.ArrayList;
import java.util.List;

public class TweetWIthUser {

    @Embedded
    User user;

    @Embedded(prefix = "tweet_")
    Tweet tweet;

    public static List<Tweet> getTweetList(List<TweetWIthUser> tweetWIthUsers) {
        List<Tweet> tweets = new ArrayList<>();
        for (int i=0; i<tweetWIthUsers.size(); i++){
            Tweet tweet = tweetWIthUsers.get(i).tweet;
            tweet.user = tweetWIthUsers.get(i).user;
            tweets.add(tweet);
        }
        return  tweets;
    }
}
