package kwetter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import kwetter.dao.TweetDAO;
import kwetter.dao.TweetDAOCollectionImpl;
import kwetter.dao.UserDAO;
import kwetter.dao.UserDAOCollectionImpl;
import kwetter.domain.Tweet;
import kwetter.domain.User;

@SessionScoped //has to be session scoped for now because of initusers()
@ManagedBean(name = "kwetter")
public class KwetterService {

    private final UserDAO userDAO = new UserDAOCollectionImpl();
    private final TweetDAO tweetDAO = new TweetDAOCollectionImpl();

    private String showdata = "tweets";
    private User selectedUser;
    private User loggedInUser;
    private List<Tweet> foundTweets;
    private String searchQuery = "";

    private Map<Tweet, User> timelineTweets;
    
    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getShowdata() {
        return showdata;
    }

    public void setShowdata(String showdata) {
        this.showdata = showdata;
    }

    public KwetterService() {
        System.out.println("Launching KwetterService");
        initUsers();
        foundTweets = new ArrayList<>();
        timelineTweets = new HashMap<>();
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
        return userDAO.find((Long) id);
    }

    public void findTweetsContaining() {
        this.foundTweets.clear();
        this.showdata = "search";
        if (!searchQuery.equals("")) {
            for (Tweet t : tweetDAO.find(searchQuery)) {
                System.out.println("Found tweet!\n" + t.getTweet());
            }
            this.foundTweets = tweetDAO.find(searchQuery);
        } else {
            this.showdata = "tweets";
        }
    }

    public List<Tweet> getFoundTweets() {
        return foundTweets;
    }

    public Tweet getLatestTweet(User u) {
        return u.getLastTweet();
    }

    public List<String> getTimeLineTweets(User u) {
        for (Map.Entry<Tweet, User> e : u.getTimeLine().entrySet()) {
            System.out.println(e.getKey().getDatum().toString() + " " + e.getValue().getName());
        }
        this.timelineTweets = u.getTimeLine();
        return new ArrayList(timelineTweets.keySet());
    }

    public Map<Tweet, User> getTimelineTweets() {
        return timelineTweets;
    }

    public void setTimelineTweets(Map<Tweet, User> timelineTweets) {
        this.timelineTweets = timelineTweets;
    }

    public String displayProfile(String username, String showdata) {
        this.selectedUser = userDAO.findUsingUsername(username);
        this.showdata = showdata;
        return String.format("testprofile?faces-redirect=true&amp;includeViewParams=true", username);
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
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

        tweetDAO.create(t1, u1);
        tweetDAO.create(t2, u1);
        tweetDAO.create(t3, u1);

        for (int i = 0; i < 50; i++) {
            tweetDAO.create(new Tweet("TestH!", new Date(), "PC"), u1);
        }
        for (int i = 0; i < 50; i++) {
            tweetDAO.create(new Tweet("TestT!", new Date(), "PC"), u3);
        }
        for (int i = 0; i < 50; i++) {
            tweetDAO.create(new Tweet("TestF!", new Date(), "PC"), u2);
        }
        for (int i = 0; i < 50; i++) {
            tweetDAO.create(new Tweet("TestS!", new Date(), "PC"), u4);
        }

        userDAO.create(u1);
        userDAO.create(u2);
        userDAO.create(u3);
        userDAO.create(u4);

        this.loggedInUser = u1;
        this.selectedUser = u1;
    }
}
