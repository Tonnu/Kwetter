package kwetter.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KwetterUser")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    private String web;
    private String bio;

    private Collection<User> following = new ArrayList();
    private Collection<User> followers = new ArrayList();
    private Collection<Tweet> tweets = new ArrayList();
    private Collection<Tweet> mentions = new ArrayList();

    private Tweet lastTweet;

    public List<Tweet> getMentions() {
        return (List<Tweet>) this.mentions;
    }

    public void addMention(Tweet t) {
        this.mentions.add(t);
    }

    public Tweet getLastTweet() {
        return lastTweet;
    }

    public void setLastTweet(Tweet lastTweet) {
        this.lastTweet = lastTweet;
    }

    public User() {
    }

    public User(String naam) {
        this.name = naam;
    }

    public User(String naam, String web, String bio) {
        this.name = naam;
        this.web = web;
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Collection<User> getFollowing() {

        return Collections.unmodifiableCollection(following);
    }

    public void setFollowing(Collection<User> following) {
        this.following = following;
    }

    public Collection<Tweet> getTweets() {
        Collections.sort((List<Tweet>) tweets, Collections.reverseOrder());
        return Collections.unmodifiableCollection(tweets);
    }

    public void setTweets(Collection<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Boolean addFollowing(User following) {
        if (isFollowing(following)) {
            return false;
        } else {
            following.addFollower(this); //add new follower to the other user;    
            this.following.add(following);
            return true;
        }
    }

    public boolean unFollow(User other) {
        if (isFollowing(other)) {
            other.followers.remove(this);
            this.following.remove(other);
            return true;
        }
        return false;
    }

    public boolean isFollowing(User other) {
        for (User user : following) {
            if (other.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public Boolean addTweet(Tweet tweet) {
        this.lastTweet = tweet;
        return this.tweets.add(tweet);

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() + bio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the name fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        return "twitter.domain.User[naam=" + name + "]";
    }

    public Collection<User> getFollowers() {
        return Collections.unmodifiableCollection(followers);
    }

    public Boolean addFollower(User _follower) {
        return this.followers.add(_follower);
    }

    public void setFollowers(Collection<User> followers) {
        this.followers = followers;
    }

    public void removeTweet(Tweet t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
