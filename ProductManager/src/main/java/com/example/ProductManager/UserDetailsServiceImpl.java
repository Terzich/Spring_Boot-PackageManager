package com.example.ProductManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userRepository.getUserByUsername(s);
        if(user==null){
            throw  new UsernameNotFoundException("Could not find a user!");
        }

        return new MyUserDetails(user);
    }

    public void signUpUser(User user, int isAdmin){
        final String encryptedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(true);
        Set<Role> roles=new HashSet<>();

        roles.add(roleService.get(isAdmin));


        user.setRoles(roles);
        final User createdUser=userRepository.save(user);
    }

}
