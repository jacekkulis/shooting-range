package com.serviceimpl;

import com.model.Role;
import com.model.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	Role userRole = roleRepository.findOne((long) 1);
	Set<Role> roles = new HashSet<>();
	roles.add(userRole);
	user.setRoles(roles);
	userRepository.save(user);
    }
    
    @Override
    public void delete(String username) {
	userRepository.delete(userRepository.findByUsername(username));
    }

    @Override
    public User findByUsername(String username) {
	return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getListOfUsers() {
	return userRepository.findAll();
    }

   

    @Override
    public User findById(int id) {
	// TODO Auto-generated method stub
	return null;
    }
}
