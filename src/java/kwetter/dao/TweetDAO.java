/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kwetter.dao;

import java.util.List;
import kwetter.domain.Tweet;
import kwetter.domain.User;

/**
 *
 * @author Toon
 */
public interface TweetDAO {
        List<Tweet> find(String query);
        
        List<Tweet> findAll();
        
        void create(Tweet t, User u);
        
        void remove(Tweet t, User u);
        
        
}
