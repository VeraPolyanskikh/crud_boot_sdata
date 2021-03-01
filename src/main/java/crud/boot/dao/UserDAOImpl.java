package crud.boot.dao;

import crud.boot.model.Role;
import crud.boot.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {


    @PersistenceContext( type = PersistenceContextType.EXTENDED)
    protected EntityManager entityManager;

    public UserDAOImpl() {

    }

    @Override
    public void saveUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void removeUserById(long id) {
        try {
            User foundUser = entityManager.find(User.class, id, LockModeType.PESSIMISTIC_WRITE);
            entityManager.remove(foundUser);
        } catch (PessimisticLockException e) {
            System.out.println("lock failed");
        }

    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public User getUserByLogin(String loginStr) {
        TypedQuery<User> query =
                entityManager.createQuery("from User u where u.login=:loginStr", User.class);
        query.setParameter("loginStr", loginStr);
        return query.getSingleResult();
    }
}
