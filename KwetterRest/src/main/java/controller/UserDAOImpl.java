/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.ws.rs.WebApplicationException;
import org.kwetter.model.User;

/**
 *
 * @author user
 */
@Stateless
public class UserDAOImpl implements UserDAO, Serializable{
    
    @PersistenceContext(name="KwetterRestPU")
    private EntityManager em;

    @Override
    public User getUser(String username) {
        return em.find(User.class, username);
    }

    @Override
    public void createUser(User user) throws EntityExistsException, EJBException{
        User user2 = getUser(user.getUserName());

        if (user2 != null) {
            throw new EntityExistsException();
        }
        
        em.persist(user);
    }

    @Override
    public void updateUser(User user) throws IllegalArgumentException  {
        User user2 = getUser(user.getUserName());

        if (user2 == null) {
            throw new IllegalArgumentException();
        }
        
        em.merge(user);
    }

    @Override
    public List<User> getAllUsers() throws PersistenceException, QueryTimeoutException {
        Query query = em.createQuery("SELECT user FROM User user", User.class);
        return (List<User>) query.getResultList();
    }
}
