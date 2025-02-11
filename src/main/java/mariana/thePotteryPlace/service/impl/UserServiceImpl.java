package mariana.thePotteryPlace.service.impl;

import mariana.thePotteryPlace.model.User;
import mariana.thePotteryPlace.repository.UserRepository;
import mariana.thePotteryPlace.service.AuthService;
import mariana.thePotteryPlace.service.IUserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl extends CrudServiceImpl<User, Long> implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthService authService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    public List<User> findUserByAuth() {
        Long authenticatedUserId = authService.getAuthenticatedUser().getId();
        User user = userRepository.findById(authenticatedUserId).orElseThrow(() -> new RuntimeException("User not found"));
        return List.of(user);
    }

    @Override
    public User save(User entity) {

        if(userRepository.existsByEmail(entity.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return super.save(entity);
    }

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return this.userRepository;
    }
}
