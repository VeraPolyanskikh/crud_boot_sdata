package crud.boot.dto;

import lombok.Data;

import java.io.Serializable;


public enum RoleItem implements Serializable {
    ADMIN,
    USER,
    OTHER;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
