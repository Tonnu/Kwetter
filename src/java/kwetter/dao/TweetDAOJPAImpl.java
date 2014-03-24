/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import kwetter.domain.Tweet;
import kwetter.domain.TweetEvent;
import kwetter.domain.User;

/**
 *
 * @author Toon
 */
@fireanddonotforgetpleasethankyou
@Stateless
public class TweetDAOJPAImpl implements TweetDAO, Serializable {

    private List<String> hashtags;
    private HashMap<String, Integer> counter;
    ValueComparator bvc;
    private TreeMap<String, Integer> sorted_counter;
    private TreeSet<Tweet> sorted_tweets;

    @PersistenceContext
    private EntityManager em;

    public TweetDAOJPAImpl() {
        counter = new HashMap<>(5000);
        hashtags = new ArrayList<>();
        bvc = new ValueComparator(counter);
        sorted_counter = new TreeMap<String, Integer>(bvc);
        sorted_tweets = new TreeSet<>();
    }

    @Override
    public List<Tweet> find(String query) {
        Query q = em.createQuery("SELECT e FROM tweet e WHERE e.tweet LIKE ?");
        String qq = "'%'" + query + "'%'";
        q.setParameter(1, qq);
        return (List<Tweet>) q.getResultList();
    }

    @Override
    public List<Tweet> findAll() {
        Query query = em.createQuery("SELECT e FROM tweet e");
        return (List<Tweet>) query.getResultList();
    }

    @Override
    public void create(@Observes TweetEvent event) {
        em.persist(event.getTweet());
    }

    @Override
    public void remove(Tweet t, User u) {
        em.remove(t);
    }

    @Override
    public Collection<Tweet> getTimelineForUser(User u) {
        List<Tweet> _tweets = new ArrayList<>();
        Query query = em.createQuery("SELECT t FROM tweet t WHERE t.user.name = ?");
        query.setParameter(1, u.getName());
        _tweets.addAll((List<Tweet>) query.getResultList());

        for (User uu : u.getFollowing()) {
            query.setParameter(1, uu.getName());
            _tweets.addAll((List<Tweet>) query.getResultList());
        }

        return _tweets;

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

}
