package com.example.user_api;

import com.example.user_api.exception.UserNotFoundException;
import com.example.user_api.service.EnvService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import com.example.user_api.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public EntityModel<User> getUser(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestParam String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String name){
        return userService.searchUserByName(name);
    }

    @PatchMapping("/{id}/email")
    public void updateUserEmail(@PathVariable Long id, @RequestParam String email){
        userService.updateUserEmail(id,email);
    }
//    @Value("${app.environment}")
//    private String environment;
    private final EnvService envService;

    public UserController(UserService userService, EnvService envService) {
        this.userService = userService;
        this.envService = envService;
    }

    @GetMapping("/env")
    public String getEnvironment() {
        return envService.getEnvironmentMessage();
    }

}
