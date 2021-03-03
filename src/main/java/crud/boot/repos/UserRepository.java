package crud.boot.repos;


import crud.boot.model.Role;
import crud.boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface
UserRepository extends JpaRepository<User,Long> {


    @Query("select u from User u where u.login=:loginStr")
    User getUserByLogin(@Param("loginStr") String loginStr);
}
