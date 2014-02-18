/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kwetter.domain.Tweet;
import kwetter.domain.User;

/**
 *
 * @author Toon
 */
public class TweetDAOCollectionImpl implements TweetDAO {

    private final List<Tweet> tweets;


    public TweetDAOCollectionImpl() {
        tweets = new ArrayList<>();
    }

    @Override
    public List<Tweet> find(String query) {
        ArrayList<Tweet> _tempTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getTweet().contains(query)) {
                _tempTweets.add(tweet);
            }
        }
        return _tempTweets;
    }

    @Override
    public List<Tweet> findAll() {
        return new ArrayList(tweets);
    }

    @Override
    public void create(Tweet t, User u) {
        u.addTweet(t);
        tweets.add(t);
    }

    @Override
    public void remove(Tweet t, User u) {
        u.removeTweet(t);
    }


}
