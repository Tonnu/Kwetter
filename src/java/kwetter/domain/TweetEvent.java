/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.domain;

import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 *
 * @author Toon
 */
public class TweetEvent {

    private Tweet tweet;
    private User tweeter;
    private final Options type;
    

    public enum Options {
        NEW_TWEET
    }

    public TweetEvent(Tweet t, User u, Options type) {
        tweet = t;
        tweeter = u;
        this.type = type;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public Options getType() {
        return type;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public User getTweeter() {
        return tweeter;
    }

    public void setTweeter(User tweeter) {
        this.tweeter = tweeter;
    }
}
