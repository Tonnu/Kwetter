/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.dao;

import java.util.Collection;
import java.util.List;
import javax.enterprise.event.Observes;
import kwetter.domain.Tweet;
import kwetter.domain.TweetEvent;
import kwetter.domain.User;

/**
 *
 * @author Toon
 */
public interface TweetDAO {

    List<Tweet> find(String query);

    List<Tweet> findAll();

    void create(@Observes TweetEvent event);

    void remove(Tweet t, User u);

    public Collection<Tweet> getTimelineForUser(User u);
    
    public void checkForMentions(List<User> users, Tweet t);
    
    public List<String> generateTrends();
}
