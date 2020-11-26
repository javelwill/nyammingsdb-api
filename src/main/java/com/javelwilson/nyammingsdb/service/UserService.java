package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.UserDto;
import com.javelwilson.nyammingsdb.entity.RoleEntity;
import com.javelwilson.nyammingsdb.entity.UserEntity;
import com.javelwilson.nyammingsdb.repository.RoleRepository;
import com.javelwilson.nyammingsdb.repository.UserRepository;
import com.javelwilson.nyammingsdb.security.UserPrincipal;
import com.javelwilson.nyammingsdb.shared.Utils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(UserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()) != null) throw new RuntimeException("Record Already Exists");

        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

        userEntity.setUserId(utils.generateUserId(30));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        Collection<RoleEntity> roleEntities = new HashSet<>();

        for(String role: userDto.getRoles()) {
            RoleEntity roleEntity = roleRepository.findByName(role);
            if(roleEntity != null) {
                roleEntities.add(roleEntity);
            }
        }

        userEntity.setRoles(roleEntities);

        userEntity = userRepository.save(userEntity);

        userDto = modelMapper.map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);

        return new UserPrincipal(userEntity);

//        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
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

    public List<UserDto> getUsers(int page, int limit) {

        if (page > 0) page = page - 1;
        if (limit > 50) limit = 50;
        
        Pageable pageRequest = PageRequest.of(page, limit);

        Page<UserEntity> userPages = userRepository.findAll(pageRequest);
        List<UserEntity> userEntities = userPages.getContent();

        Type listType = new TypeToken<List<UserDto>>(){}.getType();
        ModelMapper modelMapper = new ModelMapper();
        List<UserDto> usersDto = modelMapper.map(userEntities, listType);

        return usersDto;
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
