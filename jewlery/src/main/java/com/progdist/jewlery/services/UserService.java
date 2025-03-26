package com.progdist.jewlery.services;

import com.progdist.jewlery.model.User;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    public List<User> getCategories() {
        return List.of();
    }

    public User getUserById(long id) {
        return new User();
    }

    public void deleteUser(long id) {
        
    }

    public User createUser(User input) {
        return new User();
    }    
    
    public User updateUser(User input) {
        return new User();
    }
}
