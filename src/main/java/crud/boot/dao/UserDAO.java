package crud.boot.dao;

import crud.boot.model.Role;
import crud.boot.model.User;

import java.util.List;

public interface UserDAO {
    void saveUser(User user);

    void removeUserById(long id);

    User getUserById(long id);

    List<User> getAllUsers();

    List<Role> getAllRoles();


    User getUserByLogin(String login);
}
