package com.example.user_api.service;

import com.example.user_api.User;
import com.example.user_api.UserRepository;
import com.example.user_api.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();   // JPA method
    }

    public User getUser(Long id){
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: "+id));
    }

    public User createUser(User user){
        return userRepo.save(user);
    }
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    public User updateUser(Long id, User updatedUser){
        User user = getUser(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        return userRepo.save(user);
    }

    public User getUserByEmail(String email){
       return userRepo.findByEmail(email)
               .orElseThrow(() -> new UserNotFoundException("User not found: "+ email));

    }
    public List<User> searchUserByName(String keyword)
    {
           return userRepo.searchByNameIgnoreCase(keyword) ;
    }

    public void deleteUser(Long id){
        userRepo.deleteById(id);
    }
}














































































