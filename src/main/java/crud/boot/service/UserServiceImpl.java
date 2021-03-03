package crud.boot.service;

import crud.boot.model.Role;
import crud.boot.model.User;
import crud.boot.repos.RoleRepository;
import crud.boot.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService {
    private final UserRepository repoUser;
    private final RoleRepository repoRole;

    private  PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repoUser,RoleRepository repoRole) {
        this.repoUser = repoUser;
        this.repoRole = repoRole;
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
        repoUser.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User user) {
        User found = repoUser.findById(id).get();
        if(user.getPasswd() != null && !user.getPasswd().equals(found.getPasswd())
                &&!passwordEncoder.matches(user.getPasswd(),found.getPasswd())) {
            user.setPasswd(passwordEncoder.encode(user.getPasswd()));
        }else{
            user.setPasswd(found.getPasswd());
        }
        repoUser.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return repoUser.findById(id).get();
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        repoUser.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return repoUser.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return repoRole.findAll();
    }

}
