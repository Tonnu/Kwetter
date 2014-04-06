/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kwetter.service;

import java.util.List;
import kwetter.domain.Tweet;
import kwetter.domain.User;

/**
 *
 * @author user
 */
public interface KwetterMaintainService {
    
    List<Tweet> findAll();
    
    List<Tweet> find(String query);
    
    List<Tweet> findAllForUser(User u);
    
    void remove(Tweet t, User u);
}
