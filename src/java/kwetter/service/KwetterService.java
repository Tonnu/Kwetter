package kwetter.service;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import kwetter.dao.UserDAO;
import kwetter.dao.UserDAOCollectionImpl;
import kwetter.domain.Tweet;
import kwetter.domain.User;

@Stateless
@ManagedBean(name="kwetter")
public class KwetterService {

    private UserDAO userDAO = new UserDAOCollectionImpl();
    
    private String showdata ="tweets";

    public String getShowdata() {
        return showdata;
    }

    public void setShowdata(String showdata) {
        this.showdata = showdata;
    }
    
    public KwetterService() {
        System.out.println("Launching KwetterService");
        initUsers();
    }

    public void create(User user) {
        userDAO.create(user);
    }

    public void edit(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove(User user) {
        userDAO.remove(user);
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User find(Object id) {
        return userDAO.find((Long)id);
    }

    public int count() {
        return userDAO.count();
    }

    private void initUsers() {
        User u1 = new User("Hans", "http", "geboren 1");
        User u2 = new User("Frank", "httpF", "geboren 2");
        User u3 = new User("Tom", "httpT", "geboren 3");
        User u4 = new User("Sjaak", "httpS", "geboren 4");
        u1.addFollowing(u2);
        u1.addFollowing(u3);
        u1.addFollowing(u4);
        
        u1.addFollower(u4);
        u1.addFollower(u3);
        u1.addFollower(u2);
        u1.addFollower(u1);
        
        Tweet t1 = new Tweet("Hallo", new Date(), "PC");
        Tweet t2 = new Tweet("Hallo again", new Date(), "PC");
        Tweet t3 = new Tweet("Hallo where are you", new Date(), "PC");
        u1.addTweet(t1);
        u1.addTweet(t2);
        u1.addTweet(t3);

        userDAO.create(u1);
        userDAO.create(u2);
        userDAO.create(u3);
        userDAO.create(u4);
    }
}
