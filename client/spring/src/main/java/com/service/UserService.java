package com.service;

import java.util.List;
import com.model.User;

public interface UserService {
    void save(User user);
    void delete(String username);
    
    User findById(int id);
    User findByUsername(String username);
    
    List<User> getListOfUsers();
}
