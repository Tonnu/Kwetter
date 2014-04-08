/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import model.User;
import service.UserResource;

/**
 *
 * @author user
 */
@Named
@RequestScoped
public class UserBean {
    //User resource handler
    private UserResource userResource;
    //List of all users
    private List<User> users;
    
    private String username;
    private User selectedUser;
    
    private String createUsername, createFirstname, createLastname;
    
    @PostConstruct
    void initialize(){
        userResource = new UserResource();
    }

    public List<User> getUsers() {
        return userResource.getAllUser();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getSelectedUser() {
        return userResource.getUser(username);
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public UserResource getUserResource() {
        return userResource;
    }

    public void setUserResource(UserResource userResource) {
        this.userResource = userResource;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getCreateLastname() {
        return createLastname;
    }

    public void setCreateLastname(String createLastname) {
        this.createLastname = createLastname;
    }

    public String getCreateFirstname() {
        return createFirstname;
    }

    public void setCreateFirstname(String createFirstname) {
        this.createFirstname = createFirstname;
    }
    
    public void createUser(){
        userResource.createUser(this.createUsername, this.createFirstname, this.createLastname);
    }
    
    public void updateUser(){
        userResource.updateUser(this.createUsername, this.createFirstname, this.createLastname);
    }
}
