/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.service;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import kwetter.dao.TweetDAO;
import kwetter.dao.UserDAO;
import kwetter.dao.fireanddonotforgetpleasethankyou;
import kwetter.domain.Role;
import kwetter.domain.Tweet;
import kwetter.domain.TweetEvent;
import kwetter.domain.User;

/**
 *
 * @author user
 */
@Startup
@Singleton
public class InitBean {

    @Inject
    @fireanddonotforgetpleasethankyou //@fireanddoforgetpleasethankyousokind
    private UserDAO userDAO;

    @Inject
    @fireanddonotforgetpleasethankyou //@fireanddoforgetpleasethankyousokind
    private TweetDAO tweetDAO;
    
    @PostConstruct
    void InitBean() {
        System.out.println("--- INITIALIZING USERS");

        User u1 = new User("Hans", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918", "http", "geboren 1"); //pass: admin
        User u2 = new User("Frank", "db605e8f71913d1f3966ad908d78b8a8084f5047122037b2b91a7192b598a9ad", "httpF", "geboren 2"); //pass: Frank
        User u3 = new User("Tom", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", "httpT", "geboren 3"); //pass:empty
        User u4 = new User("Sjaak", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", "httpS", "geboren 4"); //pass: empty

        userDAO.create(u1);
        userDAO.create(u2);
        userDAO.create(u3);
        userDAO.create(u4);

        u1.addRole(new Role("admin"));
        u1.addRole(new Role("user"));
        u2.addRole(new Role("user"));
        u3.addRole(new Role("user"));

        u1.addFollowing(u2);
        u1.addFollowing(u3);
        u1.addFollowing(u4);

        u4.addFollowing(u1);
        u3.addFollowing(u1);
        u2.addFollowing(u1);

        userDAO.edit(u1);
        userDAO.edit(u2);
        userDAO.edit(u3);
        userDAO.edit(u4);

        System.out.println("--- USERS INITIALIZED");

        System.out.println("--- INITIALIZING TWEETS");
        Tweet t1 = new Tweet("Hallo", new Date(), "PC", u1);
        Tweet t2 = new Tweet("Hallo again", new Date(), "PC", u1);
        Tweet t3 = new Tweet("Hallo where are you", new Date(), "PC", u1);

        TweetEvent te = new TweetEvent(t1, u1, TweetEvent.Options.NEW_TWEET);
        TweetEvent te1 = new TweetEvent(t2, u1, TweetEvent.Options.NEW_TWEET);
        TweetEvent te2 = new TweetEvent(t3, u1, TweetEvent.Options.NEW_TWEET);

        tweetDAO.create(te);
        tweetDAO.create(te1);
        tweetDAO.create(te2);
        System.out.println("--- TWEETS INITIALIZED");
    }
}
