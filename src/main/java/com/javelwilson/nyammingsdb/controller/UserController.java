package com.javelwilson.nyammingsdb.controller;

import com.javelwilson.nyammingsdb.dto.UserDto;
import com.javelwilson.nyammingsdb.model.PasswordResetRequestModel;
import com.javelwilson.nyammingsdb.model.ResetPasswordRequestModel;
import com.javelwilson.nyammingsdb.model.UserRequestModel;
import com.javelwilson.nyammingsdb.model.UserResponseModel;
import com.javelwilson.nyammingsdb.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public UserResponseModel getUser(@PathVariable String id) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = userService.getUserById(id);

        UserResponseModel userResponseModel = modelMapper.map(userDto, UserResponseModel.class);
        return userResponseModel;
    }

    @GetMapping(consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public List<UserResponseModel> getUsers(@RequestParam(value="page", defaultValue = "0") int page,
                                            @RequestParam(value="limit", defaultValue = "25") int limit ) {
        ModelMapper modelMapper = new ModelMapper();
        List<UserDto> usersDto = userService.getUsers(page, limit);

        Type listType = new TypeToken<List<UserResponseModel>>() {
        }.getType();
        List<UserResponseModel> usersResponseModel = modelMapper.map(usersDto, listType);
        return usersResponseModel;
    }

    @DeleteMapping(path = "/{userId}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public void deleteUser(@PathVariable String userId) {
        ModelMapper modelMapper = new ModelMapper();
        userService.deleteUser(userId);
    }

    @PatchMapping(path = "/{userId}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public UserResponseModel updateUser(@PathVariable String userId, @RequestBody UserRequestModel userRequestModel) {

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);

        userDto = userService.updateUser(userId, userDto);

        UserResponseModel userResponseModel = modelMapper.map(userDto, UserResponseModel.class);
        return userResponseModel;
    }

    @PostMapping(consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public UserResponseModel createUser(@RequestBody UserRequestModel userRequestModel) {
        UserResponseModel userResponseModel;

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);
        userDto.setRoles(new HashSet<>(Arrays.asList("ROLE_USER")));

        userDto = userService.createUser(userDto);
        userResponseModel = modelMapper.map(userDto, UserResponseModel.class);

        return userResponseModel;
    }

    @GetMapping(path = "/email-verification", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public void verifyEmailToken(@RequestParam(value="token") String token) {
        boolean isVerified = userService.verifyEmailToken(token);
        if (!isVerified) {
            throw new RuntimeException("Token Not Verified");
        }
    }

    @PostMapping(path="/password-reset-request", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public void passwordReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
        userService.requestPassordReset(passwordResetRequestModel.getEmail());
    }

    @PostMapping(path="/reset-password", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public void resetPassword(@RequestBody ResetPasswordRequestModel resetPasswordRequestModel) {
        userService.resetPassword(resetPasswordRequestModel.getToken(), resetPasswordRequestModel.getPassword());
    }
}
