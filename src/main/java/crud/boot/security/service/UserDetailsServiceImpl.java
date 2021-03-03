package crud.boot.security.service;

import crud.boot.model.User;
import crud.boot.repos.UserRepository;
import crud.boot.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl extends BaseService implements UserDetailsService {

    @Autowired
    private  UserRepository repo;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repo.findOne(new Example<User>() {
            @Override
            public User getProbe() {
                User user = new User();
                user.setLogin(s);
                return user;
            }

            @Override
            public ExampleMatcher getMatcher() {
                return ExampleMatcher.matching();
            }
        }).get();
    }
}
