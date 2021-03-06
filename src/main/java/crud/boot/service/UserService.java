package crud.boot.service;

import crud.boot.dto.RoleItem;
import crud.boot.dto.UserDto;
import crud.boot.model.Role;
import crud.boot.model.User;

import java.util.List;

public interface UserService  {
    void saveUser(UserDto user);

    UserDto getUser(long id);

    void updateUser(Long id, UserDto user);

    void removeUserById(long id);

    List<UserDto> getAllUsers();

    List<RoleItem> getAllRoles();
}
