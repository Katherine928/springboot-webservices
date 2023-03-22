package net.katherine.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.katherine.springboot.dto.UserDto;
import net.katherine.springboot.entity.User;
import net.katherine.springboot.exception.ResourceNotFoundException;
import net.katherine.springboot.mapper.AutoUserMapper;
import net.katherine.springboot.mapper.UserMapper;
import net.katherine.springboot.repository.UserRepository;
import net.katherine.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        // Convert UserDao into User JPA Entity
        //User user = UserMapper.mapToUser(userDto);
        //User user = modelMapper.map(userDto, User.class);
        User user = AutoUserMapper.MAPPER.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        //Convert User JPA Entity to UserDto
        //UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);
        //UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        //return savedUserDto;
        return AutoUserMapper.MAPPER.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
       User user = userRepository.findById(userId).orElseThrow(
               () -> new ResourceNotFoundException("User", "id", userId)
       );
        //return modelMapper.map(user, UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        //return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
        //return users.stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return users.stream().map(user -> AutoUserMapper.MAPPER.mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", user.getId())
        );
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);
        //return UserMapper.mapToUserDto(updatedUser);
        //return modelMapper.map(updatedUser, UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );
        userRepository.deleteById(userId);
    }
}
