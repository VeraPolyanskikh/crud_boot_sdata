package crud.boot.controller;

import crud.boot.model.Role;
import org.springframework.format.Formatter;

import java.util.Locale;

public class RoleFormatter implements Formatter<Role> {


    @Override
    public Role parse(String id, Locale locale) {
        Role role = new Role();
        role.setRoleName(Role.RoleItem.valueOf(id));
        return role;
    }

    @Override
    public String print(Role role, Locale locale) {
        return role.getRoleName().name();
    }
}
