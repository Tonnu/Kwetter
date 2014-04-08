/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kwetter.kwetterrest;

import controller.UserDAO;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.kwetter.model.User;

/**
 *
 * @author user
 */
@Startup
@Singleton
public class InitializationBean implements Serializable{
    
    @Inject
    UserDAO userDAO;
    
    @PostConstruct
    void initializeUser(){
        System.out.println("--- INITIALIZING USERS");
        User u1 = new User("Hans", "Hans", "van H");
        User u2 = new User("Frank", "Frank", "C");
        try{
            userDAO.createUser(u1);
            userDAO.createUser(u2);
        } catch(Exception ex) {
            System.out.println("Error while initialing users");
        }
        System.out.println("--- INITIALIZED USERS");
    }
}
