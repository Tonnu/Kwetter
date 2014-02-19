/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kwetter.domain;

/**
 *
 * @author Toon
 */
public class TimelineTweet implements Comparable<TimelineTweet> {

    private Tweet tweet;
    private User submitter;

    public TimelineTweet(Tweet t, User u) {
        this.tweet = t;
        this.submitter = u;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public User getSubmitter() {
        return submitter;
    }

    @Override
    public int compareTo(TimelineTweet t) {
        return this.tweet.getDatum().compareTo(t.getTweet().getDatum());
    }
    
    
}
