package kwetter.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import javax.inject.Inject;
import javax.inject.Named;
import kwetter.dao.TweetDAO;
import kwetter.dao.TweetDAOCollectionImpl;
import kwetter.dao.UserDAO;
import kwetter.dao.UserDaoQualifier;
import kwetter.domain.Tweet;
import kwetter.domain.User;

@Named("kwetter")
@SessionScoped //has to be session scoped for now because of initusers(
public class KwetterServiceImpl implements Serializable, KwetterService{
    
    @Inject
    private UserDAO userDAO;
    
    @Inject
    private TweetDAO tweetDAO;

    private String showdata = "tweets";
    private User selectedUser;

    private User loggedInUser;
    private List<Tweet> foundTweets;
    private String searchQuery = "";
    private String newTweet = "";
    private String loginUserName= "";

    @Override
    public String getLoginUserName() {
        return loginUserName;
    }

    @Override
    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }
    
    @Override
    public Date getCurrentDate() {
        return new Date();
    }

    private List<Tweet> tlTweets;
    private List<User> loginUsers;
    private static Random r = new Random();

    @Override
    public List<Tweet> getTlTweets() {
        return tlTweets;
    }

    @Override
    public List<Tweet> getTimelineForUser(User u) {
        this.tlTweets = (List<Tweet>) tweetDAO.getTimelineForUser(u);
        return this.tlTweets;
    }

    @Override
    public String getNewTweet() {
        return newTweet;
    }

    @Override
    public void setNewTweet(String newTweet) {
        this.newTweet = newTweet;
    }

    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public String getSearchQuery() {
        return searchQuery;
    }

    @Override
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Override
    public String getShowdata() {
        return showdata;
    }

    @Override
    public void setShowdata(String showdata) {
        this.showdata = showdata;
    }
    
    @PostConstruct
    private void initKwetterServiceImpl() {
        System.out.println("--- Launching KwetterService");
        foundTweets = new ArrayList<>();
        this.tlTweets = new ArrayList<>();
        loginUsers = new ArrayList<>();
        initUsers();
    }

    @Override
    public void create(User user) {
        userDAO.create(user);
    }

    @Override
    public void submitNewTweet(User submitter, String tweet) {
        System.out.println("Submitting new tweet: " + tweet);
        Tweet _newtweet = new Tweet(tweet, new Date(), "PC", submitter.getName());
        tweetDAO.create(_newtweet, submitter);
        tweetDAO.checkForMentions(this.userDAO.findAll(), _newtweet);
        this.newTweet = "";
    }

    @Override
    public List<String> getLatestTrends() {
        return tweetDAO.generateTrends();
    }

    @Override
    public void edit(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(User user) {
        userDAO.remove(user);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User find(Object id) {
        return userDAO.find((Long) id);
    }

    @Override
    public List<Tweet> getMentionsForUser(User u) {
        return u.getMentions();
    }

    @Override
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

    @Override
    public List<Tweet> getFoundTweets() {
        return foundTweets;
    }

    @Override
    public Tweet getLatestTweet(User u) {
        return u.getLastTweet();
    }

    @Override
    public String displayProfile(String username, String showdata) {
        this.selectedUser = userDAO.findUsingUsername(username);
        this.showdata = showdata;
        return String.format("testprofile?faces-redirect=true&amp;includeViewParams=true", username);
    }

    @Override
    public User getSelectedUser() {
        return selectedUser;
    }

    @Override
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    @Override
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
        System.out.println("users initialized");

        this.loginUsers = userDAO.findAll();
        this.tlTweets.addAll(u1.getTweets());

    }

    @Override
    public String login() {
        this.loggedInUser = userDAO.findUsingUsername(this.loginUserName);
        this.selectedUser = userDAO.findUsingUsername(this.loginUserName);
        return "index?faces-redirect=true&amp;includeViewParams=true";
    }

    @Override
    public List<User> getLoginUsers() {
        return loginUsers;
    }
}
