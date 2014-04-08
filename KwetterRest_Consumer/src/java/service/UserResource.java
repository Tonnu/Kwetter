/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import java.util.List;
import javax.el.ELException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.User;

/**
 *
 * @author user
 */
public class UserResource {
    
    String mediaType;
    WebTarget root;

    public UserResource() {
        mediaType = MediaType.APPLICATION_JSON;
        Client client = ClientBuilder.newClient();
        root = client.target("http://localhost:8080/KwetterRest/webresources/user");
    }

    public List<User> getAllUser() {
        GenericType<List<User>> listm = new GenericType<List<User>>() {
        };
        List<User> users = root.request(MediaType.APPLICATION_JSON).get(listm);
        return users;
    }

    public User getUser(String username) {
        User user = null;
        if (username != null) {
            try{
                WebTarget target = root.path(username);
                System.out.println(target.getUri().toString());
                user = target.request(mediaType).get(User.class);
            }catch(NotFoundException ex){
                
            }
        }
        return user;
    }
    
    public void createUser(String username, String firstname, String lastname) {
        User user = new User(username, firstname, lastname);
        Invocation buildPost = root.request(mediaType).buildPost(Entity.entity(user, mediaType));
        buildPost.invoke();
    }

    public void updateUser(String createUsername, String createFirstname, String createLastname) {
        User user = new User(createUsername, createFirstname, createLastname);
        Invocation buildPost = root.request(mediaType).buildPut(Entity.entity(user, mediaType));
        buildPost.invoke();
    }

}
