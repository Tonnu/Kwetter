package kwetter.service;

import Interceptors.TweetInterceptor;
import com.google.common.eventbus.Subscribe;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;

import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import kwetter.dao.TweetDAO;
import kwetter.dao.UserDAO;
import kwetter.dao.fireanddonotforgetpleasethankyou;
import kwetter.domain.Tweet;
import kwetter.domain.TweetEvent;
import kwetter.domain.TweetEvent.Options;
import kwetter.domain.User;
import kwetter.domain.UserEvent;

@Named("kwetter")
@Stateless //has to be session scoped for now because of initusers(
public class KwetterServiceImpl implements Serializable, KwetterService {

    @Inject
    @fireanddonotforgetpleasethankyou
    private UserDAO userDAO;

    @Inject
    @fireanddonotforgetpleasethankyou
    private TweetDAO tweetDAO;

    private String showdata = "tweets";
    private User selectedUser;

    private User loggedInUser;
    private List<Tweet> foundTweets;
    private String searchQuery = "";
    private String newTweet = "";
    private String loginUserName = "";

    @Inject
    private Event<UserEvent> userEvents;

    @Inject
    private Event<TweetEvent> tweetEvents;

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
        System.out.println("getting timeline for user: " + u.getName());
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
    public void stopFollowing(User toUnFollow) {
        UserEvent ue = new UserEvent(toUnFollow, UserEvent.userOptions.STOP_FOLLOW);
        userEvents.fire(ue);
    }

    @Override
    public void addFollower(User toFollow) {
        UserEvent ue = new UserEvent(toFollow, UserEvent.userOptions.NEW_FOLLOW);
        userEvents.fire(ue);
    }

    @Override
    public void create(User user) {
        userDAO.create(user);
    }

    @Override
    @Interceptors(TweetInterceptor.class)
    public void submitNewTweet(User submitter, String tweet) {
        Tweet t = new Tweet(tweet, new Date(), "PC", submitter);
        TweetEvent event = new TweetEvent(t, submitter, Options.NEW_TWEET);
        tweetEvents.fire(event);
        tweetDAO.checkForMentions(this.userDAO.findAll(), event.getTweet());
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
        System.out.println("users initialized");

        User u1 = new User("Hans", "http", "geboren 1");
        User u2 = new User("Frank", "httpF", "geboren 2");
        User u3 = new User("Tom", "httpT", "geboren 3");
        User u4 = new User("Sjaak", "httpS", "geboren 4");
        userDAO.create(u1);
        userDAO.create(u2);
        userDAO.create(u3);
        userDAO.create(u4);

        u1.addFollowing(u2);
        u1.addFollowing(u3);
        u1.addFollowing(u4);

        u4.addFollowing(u1);
        u3.addFollowing(u1);
        u2.addFollowing(u1);

        Tweet t1 = new Tweet("Hallo", new Date(), "PC", u1);
        Tweet t2 = new Tweet("Hallo again", new Date(), "PC", u1);
        Tweet t3 = new Tweet("Hallo where are you", new Date(), "PC", u1);

        TweetEvent te = new TweetEvent(t1, u1, Options.NEW_TWEET);
        TweetEvent te1 = new TweetEvent(t2, u1, Options.NEW_TWEET);
        TweetEvent te2 = new TweetEvent(t3, u1, Options.NEW_TWEET);

        tweetEvents.fire(te);
        tweetEvents.fire(te1);
        tweetEvents.fire(te2);

        this.loginUsers = userDAO.findAll();
        //this.tlTweets.addAll(u1.getTweets());

    }

    @Override
    @Subscribe
    public String login() {
        UserEvent e = new UserEvent(userDAO.findUsingUsername(this.loginUserName), UserEvent.userOptions.LOGIN);
        userEvents.fire(e);
        return "index?faces-redirect=true&amp;includeViewParams=true";
    }

    @Override
    public List<User> getLoginUsers() {
        return loginUsers;
    }

    public void onUserEvent(@Observes UserEvent event) {
        System.out.println("UserEvent got fired");
        switch (event.getType()) {
            case LOGIN:
                System.out.println("New login event");
                this.loggedInUser = event.getU();
                this.selectedUser = event.getU();
                tweetDAO.getTimelineForUser(this.loggedInUser);
                break;
            case LOGOUT:
                System.out.println("New logout event");
                this.loggedInUser = null;
                break;
            case NEW_FOLLOW:
                System.out.println("Now following " + event.getU().getName());
                this.loggedInUser.addFollowing(event.getU());
                tweetDAO.getTimelineForUser(this.loggedInUser);
                break;
            case STOP_FOLLOW:
                System.out.println("Stopped following " + event.getU().getName());
                this.loggedInUser.unFollow(event.getU());
                tweetDAO.getTimelineForUser(this.loggedInUser);
                break;
            default:
                System.out.println("Could not find event type  " + event.getType().toString());
                break;
        }
    }
}
