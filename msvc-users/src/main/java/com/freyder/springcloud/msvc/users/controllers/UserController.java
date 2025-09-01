package com.freyder.springcloud.msvc.users.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.freyder.springcloud.msvc.users.entities.User;
import com.freyder.springcloud.msvc.users.services.IUserService;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    /* Method createUser:WithOut CLEAN CODE ➡
     * 
     * @PostMapping
     * public ResponseEntity<User> createUser(@RequestBody User user) {
     * User savedUser = userService.save(user);
     * return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
     * }
     */
    // With Clean Code 👇
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("UserController::createUser: creando {}", user);

        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    /* Method updateUser:WithOut CLEAN CODE ➡
     * 
     * @PutMapping("/{id}")
     * public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable
     * Long id) {
     * Optional<User> userUpdateOptional = userService.update(user, id);
     * 
     * return userUpdateOptional
     * .map(userUpdated ->
     * ResponseEntity.status(HttpStatus.CREATED).body(userUpdated))
     * .orElseGet(() -> ResponseEntity.notFound().build());
     * }
     */
    // With Clean Code 👇
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
        logger.info("UserController::updateUser: actualizando {}", user);

        return userService.update(user, id)
                .map(userUpdated -> ResponseEntity.status(HttpStatus.CREATED).body(userUpdated))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /* Method createUser:WithOut CLEAN CODE ➡
     * 
     * @GetMapping("/{id}")
     * public ResponseEntity<User> getUserById(@PathVariable Long id) {
     * Optional<User> user = userService.findById(id);
     * return user.map(ResponseEntity::ok)
     * .orElseGet(() -> ResponseEntity.notFound().build());
     * }
     * 
     */
    // With Clean Code 👇
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("UserController::getUserById: obteniendo user con id {}", id);

        return  userService.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /* Method createUser:WithOut CLEAN CODE ➡
     * 
     * @GetMapping("/username/{username}")
     * public ResponseEntity<User> getUserByUsername(@PathVariable String username)
     * {
     * Optional<User> user = userService.findByUsername(username);
     * return user.map(ResponseEntity::ok)
     * .orElseGet(() -> ResponseEntity.notFound().build());
     * }
     */
    // With Clean Code 👇
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        logger.info("UserController::getUserByUsername: login con {} ", username);

        return userService.findByUsername(username).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        logger.info("UserController::getAllUsers: listando usuarios");
        
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("UserController::deleteUser: eliminando user con id {}", id);

        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
