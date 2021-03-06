package crud.boot.service;

import crud.boot.dto.RoleItem;
import crud.boot.dto.UserDto;
import crud.boot.model.Role;
import crud.boot.model.User;
import crud.boot.repos.RoleRepository;
import crud.boot.repos.UserRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService {
    private final UserRepository repoUser;
    private final RoleRepository repoRole;

    private final ModelMapper mapper;

    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Role.class, RoleItem.class).setConverter(context -> context.getSource().getRoleName());
        mapper.createTypeMap(RoleItem.class, Role.class).setConverter(context -> new Role(context.getSource()));
    }


    @Autowired
    public UserServiceImpl(UserRepository repoUser, RoleRepository repoRole,ModelMapper mapper) {
        this.repoUser = repoUser;
        this.repoRole = repoRole;
        this.mapper = mapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void saveUser(UserDto dto) {
        if (dto.getPasswd() != null) {
            dto.setPasswd(passwordEncoder.encode(dto.getPasswd()));
        }
        User user =mapper.map(dto, User.class);
        repoUser.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserDto dto) {
        User found = repoUser.findById(id).get();
        if (dto.getPasswd() != null && !dto.getPasswd().equals(found.getPasswd())
                && !passwordEncoder.matches(dto.getPasswd(), found.getPasswd())) {
            dto.setPasswd(passwordEncoder.encode(dto.getPasswd()));
        } else {
            dto.setPasswd(found.getPasswd());
        }
        User user = mapper.map(dto, User.class);
        repoUser.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(long id) {
        return mapper.map(repoUser.findById(id).get(), UserDto.class);
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        repoUser.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return repoUser.findAll().stream().map(p -> mapper.map(p,UserDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleItem> getAllRoles() {
        return repoRole.findAll().stream().map(p -> mapper.map(p,RoleItem.class)).collect(Collectors.toList());
    }

}
