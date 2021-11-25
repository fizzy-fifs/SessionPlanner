package user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public String addNewUser(User user) {
        userRepository.insert(user);
        return "User created successfully";
    }

    public List<User> getUsers() { return userRepository.findAll(); }
}
