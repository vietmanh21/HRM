package com.loizenai.jwtauthentication.config;

import com.loizenai.jwtauthentication.model.Role;
import com.loizenai.jwtauthentication.model.RoleName;
import com.loizenai.jwtauthentication.model.User;
import com.loizenai.jwtauthentication.repository.RoleRepository;
import com.loizenai.jwtauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class SetupLoadData {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    public void loadData() {
        Role managerRole = new Role(RoleName.ROLE_MANAGER);
        Role devRole = new Role(RoleName.ROLE_DEVELOPER);

        Set<Role> roles = new HashSet<>();
        roles.add(managerRole);
        roles.add(devRole);
        roleRepository.saveAll(roles);

        User user = new User("manager", passwordEncoder.encode("123456"),
                roles, LocalDate.of(2025, 10, 10));

        userRepository.save(user);
    }
}
