package com.demo1.demo.service;

import com.demo1.demo.dto.TaskDto;
import com.demo1.demo.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    List<UserDto> getUsers();
    UserDto getUser(Long id);
    UserDto updateUser(Long id, UserDto user);
    Boolean deleteUser(Long id);
    UserDto authenticateUser(String mail, String password);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
