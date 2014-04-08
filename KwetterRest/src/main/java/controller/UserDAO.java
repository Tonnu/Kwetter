/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.List;
import org.kwetter.model.User;

/**
 *
 * @author user
 */
public interface UserDAO {
    
    User getUser(String username);
    
    void createUser(User user);
    
    void updateUser(User user);
    
    List<User> getAllUsers();
}
