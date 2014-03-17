/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kwetter.service;

import java.util.Date;
import java.util.List;
import kwetter.domain.Tweet;
import kwetter.domain.User;

/**
 *
 * @author user
 */
public interface KwetterService {

    int count();

    void create(User user);

    String displayProfile(String username, String showdata);

    void edit(User user);

    User find(Object id);

    List<User> findAll();

    void findTweetsContaining();

    Date getCurrentDate();

    List<Tweet> getFoundTweets();

    List<String> getLatestTrends();

    Tweet getLatestTweet(User u);
    
    String getLoginUserName();
    
    User getLoggedInUser();

    List<User> getLoginUsers();

    List<Tweet> getMentionsForUser(User u);

    String getNewTweet();

    String getSearchQuery();

    User getSelectedUser();

    String getShowdata();

    List<Tweet> getTimelineForUser(User u);

    List<Tweet> getTlTweets();

    String login();

    void remove(User user);

    void setLoggedInUser(User loggedInUser);

    void setLoginUserName(String loginUserName);

    void setNewTweet(String newTweet);

    void setSearchQuery(String searchQuery);

    void setSelectedUser(User selectedUser);

    void setShowdata(String showdata);

    void submitNewTweet(User submitter, String tweet);
    
    void addFollower(User toFollow);
    
    void stopFollowing(User toUnFollow);
}
