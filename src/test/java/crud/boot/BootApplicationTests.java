package crud.boot;

import crud.boot.dto.RoleItem;
import crud.boot.model.Role;
import crud.boot.model.User;
import crud.boot.repos.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@SpringBootTest
class BootApplicationTests {

    @Autowired
    private UserRepository userRepo;

    @Test
    @Transactional
    @Commit
    @Order(1)
    public void testSaveReporitory() {
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role(RoleItem.USER));
        roles.add(new Role(RoleItem.OTHER));
        User user = new User("yaya55","yaya","yaya","yaya",(byte)15,roles);
                userRepo.save(user);
    }

    @Test
    @Transactional
    @Order(2)
    public void testGetUserByLogin() { testSaveReporitory();
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role(RoleItem.USER));
        roles.add(new Role(RoleItem.OTHER));
        User user = userRepo.getUserByLogin("yaya55");
        //System.out.println(user.getRoles());
        Assertions.assertEquals(user.getLastName(),"yaya");
        Assertions.assertEquals(user.getRoles(),roles);
    }
}
