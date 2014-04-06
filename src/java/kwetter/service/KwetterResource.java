/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kwetter.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import kwetter.dao.UserDAO;
import kwetter.dao.fireanddonotforgetpleasethankyou;
import kwetter.domain.User;

/**
 *
 * @author user
 */
@Path("KwetterRest")
@Singleton
public class KwetterResource {
    
    @Inject @fireanddonotforgetpleasethankyou
    private UserDAO userDAO;
    
     @GET
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized User getUser(@Context UriInfo uriInfo, @PathParam("username") String username) {
        User user = null;
        user = userDAO.findUsingUsername(username);
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
            users = userDAO.findAll();
        }catch(PersistenceException ex ){ 
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        
        if (users.size() > 0) {
            return users;
        } else {
            throw new WebApplicationException(Response.Status.NO_CONTENT);
        }
    }
}
