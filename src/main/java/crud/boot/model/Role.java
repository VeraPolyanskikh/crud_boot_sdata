package crud.boot.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

// Этот класс реализует интерфейс GrantedAuthority, в котором необходимо переопределить только один метод getAuthority() (возвращает имя роли).
// Имя роли должно соответствовать шаблону: «ROLE_ИМЯ», например, ROLE_USER.
@Entity
public class Role implements GrantedAuthority {

    @Id
    @Enumerated(EnumType.STRING)
    private RoleItem roleName;

    public Role() {

    }

    public Role(RoleItem roleName) {
        this.roleName = roleName;
    }

    public RoleItem getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleItem role) {
        this.roleName = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName=" + roleName +
                '}';
    }

    @Override
    public String getAuthority() {
        return roleName.getAuthority();
    }

    public enum RoleItem {
        ADMIN,
        USER,
        OTHER;
        public String getAuthority() {
            return "ROLE_" + name();
        }
    }
}
