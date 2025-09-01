package com.freyder.springcloud.msvc.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freyder.springcloud.msvc.users.entities.Role;
import com.freyder.springcloud.msvc.users.entities.User;
import com.freyder.springcloud.msvc.users.repositories.RoleRepository;
import com.freyder.springcloud.msvc.users.repositories.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        // We take the same password that is received in the user object and Encripted
        // before save it
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // We have to assign the role:
        user.setRoles(getRoles(user));
        user.setEnabled(true);
        return userRepository.save(user);
    }
    
    @Override
    @Transactional
    public Optional<User> update(User user, Long id) {

        Optional<User> userOptional = this.findById(id);

        return userOptional.map(userDb -> {
            userDb.setEmail(user.getEmail());
            userDb.setUsername(user.getUsername());
            if (user.isEnabled() == null) {
                userDb.setEnabled(true);
            }else{
                userDb.setEnabled(user.isEnabled());
            }
            userDb.setRoles(getRoles(user));

            return Optional.of(userRepository.save(userDb));
        }).orElseGet(() -> Optional.empty());
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private List<Role> getRoles(User user) {
        List<Role> roles = new ArrayList<>();
        // Then let's look for the BD
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        // luego emitimos el role, solamente si esta presente y se lo agregamos:
        roleOptional.ifPresent(role -> roles.add(role));

        if (user.isAdmin()) {
            Optional<Role> adminRoleOptional= roleRepository.findByName("ROLE_ADMIN");
            adminRoleOptional.ifPresent(roles::add);            
        }
        return roles;
    }
}
