package com.demo1.demo.service.Impl;

import com.demo1.demo.dto.UserDto;
import com.demo1.demo.entity.User;
import com.demo1.demo.repository.TaskRepository;
import com.demo1.demo.repository.UserRepository;
import com.demo1.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final TaskRepository taskRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        try {
            User user = modelMapper.map(userDto, User.class);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setUpdateBy("Yönetici");
            user.setUpdatedAt(new Date());
            user.setCreatedDate(new Date());
            return modelMapper.map(userRepository.save(user), UserDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user", e);
        }
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> modelMapper.map(value, UserDto.class)).orElse(null);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFullname(userDto.getFullname());
            user.setMail(userDto.getMail());
            if (userDto.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            user.setUpdatedAt(new Date());
            user.setUpdateBy("Yönetici");
            return modelMapper.map(userRepository.save(user), UserDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserDto authenticateUser(String mail, String password) {
        Optional<User> userOpt = userRepository.findByMail(mail);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return modelMapper.map(user, UserDto.class);
            }
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByMail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), new ArrayList<>());
    }
}
