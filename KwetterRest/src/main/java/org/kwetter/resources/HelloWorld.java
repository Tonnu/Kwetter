/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kwetter.resources;


import com.google.gson.Gson;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author user
 */
@Path("HelloWorld")
@Singleton
public class HelloWorld {
    
    @GET
    @Path("{username}")
    @Produces("application/json")
    public synchronized String getHelloWorld(@Context UriInfo uriInfo, @PathParam("username") String username) {
        Gson gson = new Gson();
        String toJson = gson.toJson(String.format("Hello %s", username));
        return toJson;
    }
}
