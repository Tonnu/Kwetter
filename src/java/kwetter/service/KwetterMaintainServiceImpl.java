/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kwetter.service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kwetter.dao.TweetDAO;
import kwetter.dao.fireanddonotforgetpleasethankyou;
import kwetter.domain.Tweet;
import kwetter.domain.User;

/**
 *
 * @author user
 */
@Named("kwetterCleaner")
@ApplicationScoped
public class KwetterMaintainServiceImpl implements KwetterMaintainService{
    
    @Inject @fireanddonotforgetpleasethankyou
    TweetDAO tweetDAO;

    @Override
    public List<Tweet> findAll() {
        return tweetDAO.findAll();
    }

    @Override
    public List<Tweet> find(String query) {
        return tweetDAO.find(query);
    }

    @Override
    public List<Tweet> findAllForUser(User u) {
        return tweetDAO.findAllForUser(u);
    }

    @Override
    public void remove(Tweet t, User u) {
        tweetDAO.remove(t, u);
    }
    
}
