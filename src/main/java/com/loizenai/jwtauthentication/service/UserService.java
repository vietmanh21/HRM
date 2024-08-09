package com.loizenai.jwtauthentication.service;

import com.loizenai.jwtauthentication.exception.DuplicatedException;
import com.loizenai.jwtauthentication.exception.NotFoundException;
import com.loizenai.jwtauthentication.message.request.UserRequest;
import com.loizenai.jwtauthentication.model.Role;
import com.loizenai.jwtauthentication.model.RoleName;
import com.loizenai.jwtauthentication.model.User;
import com.loizenai.jwtauthentication.repository.RoleRepository;
import com.loizenai.jwtauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public String add(UserRequest userRequest) {
        if(userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicatedException("Username is already taken!");
        }

        User user = new User(userRequest.getUsername(),
                encoder.encode(userRequest.getPassword()));

        Set<String> strRoles = userRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch(role) {
                case "manager":
                    Role managerRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(managerRole);

                    break;
                case "developer":
                    Role devRole = roleRepository.findByName(RoleName.ROLE_DEVELOPER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(devRole);

                    break;
            }
        });

        user.setRoles(roles);
        user.setValidUtil(userRequest.getValidUtil());
        user.setValid(true);
        userRepository.save(user);

        return "Create user successfully!";
    }

    public User getUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found"));
        return user;
    }

    public List<User> list() {
        return userRepository.findAll();
    }


    public void update(long userId, UserRequest userRequest) {
        User userUpdate = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found"));

        if(userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicatedException("Username is already taken!");
        }

        User user = new User(userRequest.getUsername(),
                encoder.encode(userRequest.getPassword()));

        Set<String> strRoles = userRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch(role) {
                case "manager":
                    Role managerRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(managerRole);

                    break;
                case "developer":
                    Role devRole = roleRepository.findByName(RoleName.ROLE_DEVELOPER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(devRole);

                    break;
            }
        });

        user.setRoles(roles);
        user.setValidUtil(userRequest.getValidUtil());
        user.setValid(true);
        userRepository.save(user);
    }


    public void delete(long userId) {
        userRepository.deleteById(userId);
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException("Not found"));
    }
}
