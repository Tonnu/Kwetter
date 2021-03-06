/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import kwetter.domain.Tweet;
import kwetter.domain.TweetEvent;
import kwetter.domain.TweetEvent.Options;
import kwetter.domain.User;

/**
 *
 * @author Toon
 */
@fireanddoforgetpleasethankyousokind
@Stateless
public class TweetDAOCollectionImpl implements TweetDAO, Serializable {

    private List<Tweet> tweets;
    private List<String> hashtags;
    private HashMap<String, Integer> counter;
    ValueComparator bvc;
    private TreeMap<String, Integer> sorted_counter;
    private TreeSet<Tweet> sorted_tweets;

    public TweetDAOCollectionImpl() {
        tweets = new ArrayList<>();
        counter = new HashMap<>(5000);
        hashtags = new ArrayList<>();
        bvc = new ValueComparator(counter);
        sorted_counter = new TreeMap<String, Integer>(bvc);
        sorted_tweets = new TreeSet<>();
    }
    
    @Override
    public int countForUser(User u){
        int amount = 0;
        for(Tweet t : tweets){
            if(t.getUser().getName().equals(u.getName())){
                amount++;
            }
        }
        return amount;
    }
    
    @Override
    public List<Tweet> findAllForUser(User u){
        List<Tweet> _tweets = new ArrayList<>();
        for(Tweet t : tweets){
            if(t.getUser().getName().equals(u.getName())){
                _tweets.add(t);
            }
        }
        return _tweets;
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
    public Collection<Tweet> getTimelineForUser(User u) {
        ArrayList<Tweet> _tweets = new ArrayList<>();
        _tweets.addAll(this.findAllForUser(u));
        
        for (User following : u.getFollowing()) {
            _tweets.addAll(this.findAllForUser(following));
        }
        
        Collections.sort(_tweets, Collections.reverseOrder());
        return _tweets;
    }

    @Override
    public void create(@Observes @fireanddoforgetpleasethankyousokind TweetEvent event) {
        System.out.println("New Collection Tweet for User: " + event.getTweet().getUser().getName());
        if (event.getType() == Options.NEW_TWEET) {
            tweets.add(event.getTweet());
            
            for(Tweet tw : tweets){
                System.out.println("Tweet " +tw.getTweet() +" User " + tw.getUser());
            }
            checkForHashtags(event.getTweet());
        }
    }

    private void checkForHashtags(Tweet t) {
        String[] split = t.getTweet().split(" ");

        for (String string : split) {
            if (string.contains("#") && string.length() > 1) {
                hashtags.add(string);
                if (counter.containsKey(string)) {
                    this.counter.put(string, counter.get(string) + 1);
                } else {
                    this.counter.put(string, 1);
                }
            }
        }
    }

    @Override
    public void checkForMentions(List<User> users, Tweet t) {
        if (t.getTweet().contains("@")) {
            for (User user : users) {
                if (t.getTweet().contains("@" + user.getName())) {
                    user.addMention(t);
                    System.out.println("Found mention for user " + user.getName());
                    System.out.println(t.getTweet());
                } else {
                    System.out.println("Tweet wasn't a mention for user " + user.getName() + ":\n" + t.getTweet());
                }
            }
        }
    }

    @Override
    public List<String> generateTrends() {
        ArrayList<String> _list = new ArrayList<>();
        sorted_counter.clear();
        sorted_counter.putAll(counter);
        int c = 0;
        for (Map.Entry<String, Integer> entry : sorted_counter.entrySet()) {
            if (c < 5) {
                String string = entry.getKey();
                Integer integer = entry.getValue();
                _list.add(string + "(" + integer + ")");
                c++;
            }
        }

        return _list;
    }

    @Override
    public void remove(Tweet t, User u) {
        u.removeTweet(t);
    }
}

class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;

    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    @Override
    public int compare(String t, String t1) {
        if (base.get(t) >= base.get(t1)) {
            return -1;
        } else {
            return 1;
        }
    }

}
