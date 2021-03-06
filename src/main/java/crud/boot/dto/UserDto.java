package crud.boot.dto;

import crud.boot.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private Long id;

    private String login;

    private String passwd;

    private String name;

    private String lastName;

    private Byte age;

    private Set<RoleItem> roles = new HashSet<>();

}
