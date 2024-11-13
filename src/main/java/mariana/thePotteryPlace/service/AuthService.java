package mariana.thePotteryPlace.service;

import mariana.thePotteryPlace.model.User;
import mariana.thePotteryPlace.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User getAuthenticatedUser(){
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
