package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.ApplicationDto;
import com.javelwilson.nyammingsdb.entity.ApplicationEntity;
import com.javelwilson.nyammingsdb.entity.UserEntity;
import com.javelwilson.nyammingsdb.repository.ApplicationRepository;
import com.javelwilson.nyammingsdb.repository.UserRepository;
import com.javelwilson.nyammingsdb.shared.Utils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    public List<ApplicationDto> getApplications(String email) {
        List<ApplicationDto> applicationsDto;

        UserEntity userEntity = userRepository.findByEmail(email);

        List<ApplicationEntity> applicationEntities = applicationRepository.findAllByUser(userEntity);

        Type listType = new TypeToken<List<ApplicationDto>>() {
        }.getType();

        ModelMapper modelMapper = new ModelMapper();
        applicationsDto = modelMapper.map(applicationEntities, listType);

        return applicationsDto;
    }

    public ApplicationDto getApplication(String email, String applicationId) {
        ApplicationDto applicationDto;

        UserEntity userEntity = userRepository.findByEmail(email);

        ApplicationEntity applicationEntity = applicationRepository.findByUserAndApplicationId(userEntity, applicationId);

        if (applicationEntity == null) {
            throw new RuntimeException("Application Not Found");
        }

        ModelMapper modelMapper = new ModelMapper();
        applicationDto = modelMapper.map(applicationEntity, ApplicationDto.class);

        return applicationDto;
    }

    public ApplicationDto patchApplication(String email, String applicationId, ApplicationDto applicationDto) {

        String name = applicationDto.getName();
        String description = applicationDto.getDescription();

        UserEntity userEntity = userRepository.findByEmail(email);

        ApplicationEntity applicationEntity = applicationRepository.findByUserAndApplicationId(userEntity, applicationId);

        if (applicationEntity == null) {
            throw new RuntimeException("Application Not Found");
        }

        if (name != null) {
            applicationEntity.setName(name);
        }

        if (description != null) {
            applicationEntity.setDescription(description);
        }

        applicationEntity = applicationRepository.save(applicationEntity);

        ModelMapper modelMapper = new ModelMapper();
        applicationDto = modelMapper.map(applicationEntity, ApplicationDto.class);

        return applicationDto;
    }

    public boolean resetApplicationKey(String email, String applicationId) {
        boolean returnValue = false;

        UserEntity userEntity = userRepository.findByEmail(email);

        ApplicationEntity applicationEntity = applicationRepository.findByUserAndApplicationId(userEntity, applicationId);

        if (applicationEntity == null) {
            throw new RuntimeException("Application Not Found");
        }

        String applicationKey = utils.generateApplicationKey(30);

        applicationEntity.setApplicationKey(applicationKey);

        applicationEntity = applicationRepository.save(applicationEntity);

        if (applicationEntity.getApplicationKey() == applicationKey) {
            returnValue = true;
        }

        return returnValue;
    }

    public ApplicationDto createApplication(String email, ApplicationDto applicationDto) {

        UserEntity userEntity = userRepository.findByEmail(email);

        ModelMapper modelMapper = new ModelMapper();
        ApplicationEntity applicationEntity = modelMapper.map(applicationDto, ApplicationEntity.class);

        applicationEntity.setUser(userEntity);
        applicationEntity.setApplicationId(utils.generateApplicationId(30));
        applicationEntity.setApplicationKey(utils.generateApplicationKey(30));
        applicationEntity = applicationRepository.save(applicationEntity);

        applicationDto = modelMapper.map(applicationEntity, ApplicationDto.class);

        return applicationDto;
    }

    public ApplicationDto deleteApplication(String email, String applicationId) {

        UserEntity userEntity = userRepository.findByEmail(email);

        ApplicationEntity applicationEntity = applicationRepository.findByUserAndApplicationId(userEntity, applicationId);

        if (applicationEntity == null) {
            throw new RuntimeException("Application Not Found");
        }

        applicationRepository.delete(applicationEntity);

        ModelMapper modelMapper = new ModelMapper();
        ApplicationDto applicationDto = modelMapper.map(applicationEntity, ApplicationDto.class);

        return applicationDto;


    }
}
