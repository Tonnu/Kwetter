package kwetter.dao;

import kwetter.domain.User;
import java.util.List;

public interface UserDAO {

    int count();

    void create(User user);

    void edit(User user);

    List<User> findAll();

    User find(Long id);
    
    User findUsingUsername(String username);

    void remove(User user);
}
