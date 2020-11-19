package com.javelwilson.nyammingsdb.controller;

import com.javelwilson.nyammingsdb.dto.UserDto;
import com.javelwilson.nyammingsdb.model.UserRequestModel;
import com.javelwilson.nyammingsdb.model.UserResponseModel;
import com.javelwilson.nyammingsdb.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public UserResponseModel createUser(@RequestBody UserRequestModel userRequestModel) {
        UserResponseModel userResponseModel = new UserResponseModel();

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);

        userDto = userService.createUser(userDto);
        userResponseModel = modelMapper.map(userDto, UserResponseModel.class);

        return userResponseModel;
    }
}
