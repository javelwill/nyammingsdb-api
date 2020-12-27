package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.UserDto;
import com.javelwilson.nyammingsdb.entity.ApplicationEntity;
import com.javelwilson.nyammingsdb.entity.PasswordResetTokenEntity;
import com.javelwilson.nyammingsdb.entity.RoleEntity;
import com.javelwilson.nyammingsdb.entity.UserEntity;
import com.javelwilson.nyammingsdb.repository.PasswordResetTokenRepository;
import com.javelwilson.nyammingsdb.repository.RoleRepository;
import com.javelwilson.nyammingsdb.repository.UserRepository;
import com.javelwilson.nyammingsdb.security.UserPrincipal;
import com.javelwilson.nyammingsdb.shared.AmazonSES;
import com.javelwilson.nyammingsdb.shared.Utils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    Utils utils;

    @Autowired
    AmazonSES amazonSES;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${application.email-verification}")
    Boolean emailVerification;

    public UserDto createUser(UserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()) != null) throw new RuntimeException("Record Already Exists");

        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

        String userId = utils.generateUserId(30);
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(userId));
        userEntity.setEmailVerificationStatus(false);

        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setApplicationId(utils.generateApplicationId(30));
        applicationEntity.setApplicationKey(utils.generateApplicationKey(30));
        applicationEntity.setName("Default Application");
        applicationEntity.setDescription("Default application");
        applicationEntity.setUser(userEntity);

        userEntity.setApplications(Arrays.asList(applicationEntity));

        Collection<RoleEntity> roleEntities = new HashSet<>();

        for (String role : userDto.getRoles()) {
            RoleEntity roleEntity = roleRepository.findByName(role);
            if (roleEntity != null) {
                roleEntities.add(roleEntity);
            }
        }

        userEntity.setRoles(roleEntities);

        userEntity = userRepository.save(userEntity);

        userDto = modelMapper.map(userEntity, UserDto.class);

        if (emailVerification) {
            amazonSES.verifyEmail(userDto);
        }

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        if (!emailVerification) {
            userEntity.setEmailVerificationStatus(true);
        }
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

    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new RuntimeException("User Not Found");
        }

        UserDto userDto = new UserDto();
        userDto.setUserId(userEntity.getUserId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        return userDto;
    }

    public List<UserDto> getUsers(int page, int limit) {

        if (page > 0) page = page - 1;
        if (limit > 50) limit = 50;

        Pageable pageRequest = PageRequest.of(page, limit);

        Page<UserEntity> userPages = userRepository.findAll(pageRequest);
        List<UserEntity> userEntities = userPages.getContent();

        Type listType = new TypeToken<List<UserDto>>() {
        }.getType();
        ModelMapper modelMapper = new ModelMapper();
        List<UserDto> usersDto = modelMapper.map(userEntities, listType);

        return usersDto;
    }

    public UserDto updateUser(String userId, UserDto userDto) {
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        String address = userDto.getAddress();
        Integer postCode = userDto.getPostCode();
        String city = userDto.getCity();
        String state = userDto.getState();
        String country = userDto.getCountry();
        String company = userDto.getCompany();
        String website = userDto.getWebsite();

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

        if (address != null) {
            userEntity.setAddress(address);
        }

        if (postCode != null) {
            userEntity.setPostCode(postCode);
        }

        if (city != null) {
            userEntity.setCity(city);
        }

        if (state != null) {
            userEntity.setState(state);
        }

        if (country != null) {
            userEntity.setCountry(country);
        }

        if (company != null) {
            userEntity.setCompany(company);
        }

        if (website != null) {
            userEntity.setWebsite(website);
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

    public boolean verifyEmailToken(String token) {
        UserEntity userEntity = userRepository.findUserByEmailVerificationToken(token);

        if (userEntity != null) {
            boolean hasTokenExpired = utils.hasTokenExpired(token);
            if (!hasTokenExpired) {
                userEntity.setEmailVerificationToken(null);
                userEntity.setEmailVerificationStatus(true);
                userRepository.save(userEntity);
                return true;
            }
        }

        return false;
    }

    public boolean requestPassordReset(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            return false;
        }

        String token = utils.generatePasswordResetToken(userEntity.getUserId());

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUsers(userEntity);
        passwordResetTokenRepository.save(passwordResetTokenEntity);

        return amazonSES.sendPasswordResetRequest(userEntity.getFirstName(), userEntity.getEmail(), token);
    }

    public boolean resetPassword(String token, String password) {

        boolean returnValue = false;

        if (utils.hasTokenExpired(token)) {
            return returnValue;
        }

        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);

        if (passwordResetTokenEntity == null) {
            return returnValue;
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        UserEntity userEntity = passwordResetTokenEntity.getUsers();
        userEntity.setEncryptedPassword(encryptedPassword);
        userEntity = userRepository.save(userEntity);

        if (userEntity != null && userEntity.getEncryptedPassword().equalsIgnoreCase(encryptedPassword)) {
            returnValue = true;
        }

        passwordResetTokenRepository.delete(passwordResetTokenEntity);

        return returnValue;


    }
}
