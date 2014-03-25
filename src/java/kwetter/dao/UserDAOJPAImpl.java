/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.dao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import kwetter.domain.User;

/**
 *
 * @author Toon
 */
@fireanddonotforgetpleasethankyou
@Stateless
public class UserDAOJPAImpl implements UserDAO, Serializable {

    @PersistenceContext(name = "KwetterPU")
    private EntityManager em;
    
    @Override
    public int count() {
        Query query = em.createQuery("SELECT COUNT(user) FROM User user", User.class);
        return query.getFirstResult();
    }

    @Override
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public void edit(User user) {
        em.merge(user);
    }

    @Override
    public List<User> findAll() {
        Query query = em.createQuery("SELECT user FROM User user", User.class);
        for (User u : (List<User>) query.getResultList()) {
            System.out.println("found users in findall: " + u.getName());
        }
        return (List<User>) query.getResultList();
    }

    @Override
    public User find(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findUsingUsername(String username) {
        Query query = em.createQuery("SELECT user FROM User user WHERE user.name = :paramUsername", User.class);
        query.setParameter("paramUsername", username);
        User foundUser = (User) query.getSingleResult();
        return foundUser ;
    }

    @Override
    public void remove(User user) {
        em.remove(em.merge(user));
    }
}
