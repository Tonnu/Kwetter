package kwetter.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import kwetter.domain.User;

@fireanddoforgetpleasethankyousokind
public class UserDAOCollectionImpl implements UserDAO, Serializable {

    private List<User> users;
    
    public UserDAOCollectionImpl() {
        users = new ArrayList();
    }

    @Override
    public int count() {
        return users.size();
    }

    @Override
    public void create(User user) {
        users.add(user);
    }

    @Override
    public void edit(User user) {
        if(this.users.contains(user)){
            this.users.remove(user);
            this.users.add(user);
        }
    }

    @Override
    public List<User> findAll() {
        return new ArrayList(users);
    }

    @Override
    public User findUsingUsername(String username) {
        for(User u: users){
            if(u.getName().equals(username)) return u;
        }
        
        return null;
    }

    @Override
    public void remove(User user) {
        users.remove(user);
    }

    @Override
    public User find(Long id) {
        return users.get(id.intValue());
    }
    
}
