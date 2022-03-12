package com.example.crowdfunding.user;

import com.example.crowdfunding.config.MyUserDetailsService;
import com.example.crowdfunding.config.jwt.JwtUtil;
import com.example.crowdfunding.interfaces.ServiceInterface;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements ServiceInterface<User> {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

   @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JwtUtil jwtTokenUtil, JwtUtil jwtTokenUtil1) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil1;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String create(User user) {
        if ( emailExists(user.getEmail()) ) { return "Email already exists"; }

        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.insert(user);
        return "User created successfully";
    }

    public ResponseEntity<Map<String,Object>> login(Map<String, String> emailAndPassword) throws Exception {
        var email = emailAndPassword.get("email");
        var password = emailAndPassword.get("password");

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password))
            ;
        }catch(BadCredentialsException e) {
            throw new Exception("Invalid email or password");
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        User user = userRepository.findByEmail(email);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("user", user);
        responseData.put("jwt", jwt);

        return ResponseEntity.ok(responseData);
    }

    @Override
    public ResponseEntity<List<User>> getAll() {
        List<User> allUsers =  userRepository.findAll();
        return ResponseEntity.ok(allUsers);
    }

    public User getUserById(String userId) throws Exception {
        ObjectId userIdToObjectId = new ObjectId(userId);

        User user = userRepository.findById(userIdToObjectId);

        if (user == null) {
            throw new Exception("User does not exist");
        }

        return user;
    }
    @Override
    public String update(String userId, User newUserInfo) {
        ObjectId userIdToObjectId = new ObjectId(userId);

        User currentUserInfo = userRepository.findById(userIdToObjectId);

        if (currentUserInfo == null) {  return "user with id " + userId + " does not exists"; }

        currentUserInfo.setName(newUserInfo.getName());
        currentUserInfo.setUserName(newUserInfo.getUserName());
        currentUserInfo.setDob(newUserInfo.getDob());
        currentUserInfo.setEmail(newUserInfo.getEmail());
        currentUserInfo.setPassword(newUserInfo.getPassword());

        userRepository.save(currentUserInfo);
        return "User has been successfully updated";
    }

    @Override
    public String delete(String userId) {
        var userIdToObjectId = new ObjectId(userId);

        User user = userRepository.findById(userIdToObjectId);

        if (user == null){ return "user with id " + userId + " does not exists"; }

        userRepository.delete(user);
        return "Your account has been deleted";
    }

    private boolean emailExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? true : false;
    }
}
