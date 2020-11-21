package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.UserDto;
import com.javelwilson.nyammingsdb.entity.UserEntity;
import com.javelwilson.nyammingsdb.model.UserRequestModel;
import com.javelwilson.nyammingsdb.repository.UserRepository;
import com.javelwilson.nyammingsdb.shared.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(UserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()) != null) throw new RuntimeException("Record Already Exists");

        ModelMapper modelMapper = new ModelMapper();

        userDto.setUserId(utils.generateUserId(30));
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

        userEntity = userRepository.save(userEntity);

        userDto = modelMapper.map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    public UserDto getUserById(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new RuntimeException("User Not Found");
        }

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        return userDto;
    }

    public UserDto updateUser(String userId, UserDto userDto) {
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new RuntimeException("User Not Found");
        }

        if (firstName != null) {
            userEntity.setFirstName(firstName);
        }

        if (lastName != null) {
            userEntity.setLastName(lastName);
        }

        userEntity = userRepository.save(userEntity);

        ModelMapper modelMapper = new ModelMapper();
        userDto = modelMapper.map(userEntity, UserDto.class);

        return userDto;
    }

    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new RuntimeException("User Not Found");
        }
        userRepository.delete(userEntity);
    }
}
