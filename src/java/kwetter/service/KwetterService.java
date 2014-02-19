package kwetter.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import kwetter.dao.TweetDAO;
import kwetter.dao.TweetDAOCollectionImpl;
import kwetter.dao.UserDAO;
import kwetter.dao.UserDAOCollectionImpl;
import kwetter.domain.Tweet;
import kwetter.domain.User;

@SessionScoped //has to be session scoped for now because of initusers()
@ManagedBean(name = "kwetter")
public class KwetterService implements Serializable{

    private final UserDAO userDAO = new UserDAOCollectionImpl();
    private final TweetDAO tweetDAO = new TweetDAOCollectionImpl();

    private String showdata = "tweets";
    private User selectedUser;

    private User loggedInUser;
    private List<Tweet> foundTweets;
    private String searchQuery = "";
    private String newTweet = "";
    private String loginUserName= "";

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }
    

    public Date getCurrentDate() {
        return new Date();
    }

    private List<Tweet> tlTweets;
    private List<User> loginUsers;
    private static Random r = new Random();

    public List<Tweet> getTlTweets() {
        return tlTweets;
    }

    public List<Tweet> getTimelineForUser(User u) {
        this.tlTweets = (List<Tweet>) tweetDAO.getTimelineForUser(u);
        return this.tlTweets;
    }

    public String getNewTweet() {
        return newTweet;
    }

    public void setNewTweet(String newTweet) {
        this.newTweet = newTweet;
    }

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
        foundTweets = new ArrayList<>();
        this.tlTweets = new ArrayList<>();
        loginUsers = new ArrayList<>();
        initUsers();

    }

    public void create(User user) {
        userDAO.create(user);
    }

    public void submitNewTweet(User submitter, String tweet) {
        System.out.println("Submitting new tweet: " + tweet);
        Tweet _newtweet = new Tweet(tweet, new Date(), "PC", submitter.getName());
        tweetDAO.create(_newtweet, submitter);
        tweetDAO.checkForMentions(this.userDAO.findAll(), _newtweet);
        this.newTweet = "";
    }

    public List<String> getLatestTrends() {
        return tweetDAO.generateTrends();
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

    public List<Tweet> getMentionsForUser(User u) {
        return u.getMentions();
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

        Tweet t1 = new Tweet("Hallo", new Date(), "PC", u1.getName());
        Tweet t2 = new Tweet("Hallo again", new Date(), "PC", u1.getName());
        Tweet t3 = new Tweet("Hallo where are you", new Date(), "PC", u1.getName());

        tweetDAO.create(t1, u1);
        tweetDAO.create(t2, u1);
        tweetDAO.create(t3, u1);

//        for (int i = 0; i < 10; i++) {
//            tweetDAO.create(new Tweet("TestD!", new Date(114, 2, 1 + r.nextInt(30)), "PC", u1.getName()), u1);
//        }
//        for (int i = 0; i < 10; i++) {
//            tweetDAO.create(new Tweet("TestC!", new Date(114, 2, 1 + r.nextInt(30)), "PC", u2.getName()), u2);
//
//        }
//        for (int i = 0; i < 10; i++) {
//            tweetDAO.create(new Tweet("TestB!", new Date(114, 2, 1 + r.nextInt(30)), "PC", u3.getName()), u3);
//
//        }
//        for (int i = 0; i < 10; i++) {
//            tweetDAO.create(new Tweet("TestA!", new Date(114, 2, 1 + r.nextInt(30)), "PC", u4.getName()), u4);
//
//        }
        userDAO.create(u1);
        userDAO.create(u2);
        userDAO.create(u3);
        userDAO.create(u4);

        
        this.loginUsers = userDAO.findAll();
        this.tlTweets.addAll(u1.getTweets());

    }

    public String login() {
        this.loggedInUser = userDAO.findUsingUsername(this.loginUserName);
        this.selectedUser = userDAO.findUsingUsername(this.loginUserName);
        return "index?faces-redirect=true&amp;includeViewParams=true";
    }

    public List<User> getLoginUsers() {
        return loginUsers;
    }
}
