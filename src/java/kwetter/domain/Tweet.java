package kwetter.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity
public class Tweet implements Serializable, Comparable<Tweet> {

    private static final long serialVersionUID = 1L;
    private String tweet;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date postDate;

    private String postedFrom;

    public Tweet() {
    }

    public Tweet(String tweet) {
        this.tweet = tweet;

    }

    public Tweet(String tweet, Date datum, String vanaf, User sender) {
        this.tweet = tweet;
        this.postDate = datum;
        this.postedFrom = vanaf;
        this.user = sender;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Date getDatum() {
        return postDate;
    }

    public void setDatum(Date datum) {
        this.postDate = datum;
    }

    public String getVanaf() {
        return postedFrom;
    }

    public void setVanaf(String vanaf) {
        this.postedFrom = vanaf;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tweet != null ? tweet.hashCode() + postDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tweet)) {
            return false;
        }
        Tweet other = (Tweet) object;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        //return "twitter.domain.Tweet[id=" + postDate.toString() + "]";
        return "tostring()";
    }

    @Override
    public int compareTo(Tweet t) {
        return this.postDate.compareTo(t.getDatum());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
