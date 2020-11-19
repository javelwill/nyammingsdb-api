package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.UserDto;
import com.javelwilson.nyammingsdb.entity.UserEntity;
import com.javelwilson.nyammingsdb.repository.UserRepository;
import com.javelwilson.nyammingsdb.shared.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    public UserDto createUser(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();

        userDto.setUserId(utils.generateUserId(30));
        userDto.setEncryptedPassword("test");

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

        userEntity = userRepository.save(userEntity);

        userDto = modelMapper.map(userEntity, UserDto.class);

        return userDto;
    }
}
