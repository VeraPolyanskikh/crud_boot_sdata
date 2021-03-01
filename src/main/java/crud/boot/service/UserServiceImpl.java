package crud.boot.service;

import crud.boot.dao.UserDAO;
import crud.boot.model.Role;
import crud.boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDao;
    private  PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder  passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if(user.getPasswd() != null){
            user.setPasswd(passwordEncoder.encode(user.getPasswd()));
        }
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User user) {
        User found = userDao.getUserById(id);
        if(user.getPasswd() != null && !user.getPasswd().equals(found.getPasswd())
                &&!passwordEncoder.matches(user.getPasswd(),found.getPasswd())) {
            user.setPasswd(passwordEncoder.encode(user.getPasswd()));
        }else{
            user.setPasswd(found.getPasswd());
        }
        userDao.saveUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return userDao.getAllRoles();
    }

}
