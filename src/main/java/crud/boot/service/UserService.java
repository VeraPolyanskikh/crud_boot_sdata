package crud.boot.service;

import crud.boot.model.Role;
import crud.boot.model.User;

import java.util.List;

public interface UserService  {
    void saveUser(User user);

    User getUser(long id);

    void updateUser(Long id, User user);

    void removeUserById(long id);

    List<User> getAllUsers();

    List<Role> getAllRoles();
}
