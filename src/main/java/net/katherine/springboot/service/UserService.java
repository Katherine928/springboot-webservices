package net.katherine.springboot.service;

import net.katherine.springboot.dto.UserDto;
import net.katherine.springboot.entity.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long userId);
    List<UserDto> getAllUsers();
    UserDto updateUser(UserDto user);
    void deleteUser(Long userId);
}
