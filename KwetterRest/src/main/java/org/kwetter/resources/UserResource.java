/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kwetter.resources;

import controller.UserDAO;
import java.util.List;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.kwetter.model.User;

/**
 *
 * @author user
 */
@Path("user")
@Singleton
public class UserResource {
    
    @Inject
    UserDAO userDAO;

    @GET
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized User getUser(@Context UriInfo uriInfo, @PathParam("username") String username) {
        User user = null;
        user = userDAO.getUser(username);
        if (user != null) {
            return user;
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    public synchronized List<User> getAllUsers(@Context UriInfo uriInfo){
        List<User> users = null;
        try{
            users = userDAO.getAllUsers();
        }catch(PersistenceException ex ){ 
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        
        if (users.size() > 0) {
            return users;
        } else {
            throw new WebApplicationException(Response.Status.NO_CONTENT);
        }
    }
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON })
    public synchronized void updateUser(User user){
        try{
            userDAO.updateUser(user);
        } catch(IllegalArgumentException ex){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch(EJBException ex){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON })
    public synchronized void createUser(User user){
        try{
            userDAO.createUser(user);
        } catch(EntityExistsException ex){
            throw new WebApplicationException(Response.Status.CONFLICT);
        } catch(EJBException ex){
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
    }
}
