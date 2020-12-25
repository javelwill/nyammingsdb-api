package com.javelwilson.nyammingsdb.controller;

import com.javelwilson.nyammingsdb.dto.ApplicationDto;
import com.javelwilson.nyammingsdb.dto.RestaurantDto;
import com.javelwilson.nyammingsdb.model.ApplicationRequestModel;
import com.javelwilson.nyammingsdb.model.ApplicationResponseModel;
import com.javelwilson.nyammingsdb.model.ResetApplicationKeyResponseModel;
import com.javelwilson.nyammingsdb.model.RestaurantResponseModel;
import com.javelwilson.nyammingsdb.service.ApplicationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping(consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public List<ApplicationResponseModel> getApplications() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ApplicationDto> applicationsDto = applicationService.getApplications(email);

        Type listType = new TypeToken<List<ApplicationResponseModel>>() {
        }.getType();

        ModelMapper modelMapper = new ModelMapper();
        List<ApplicationResponseModel> applicationsResponseModel = modelMapper.map(applicationsDto, listType);

        return applicationsResponseModel;
    }

    @GetMapping(path = "/{applicationId}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public ApplicationResponseModel getApplication(@PathVariable String applicationId) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ApplicationDto applicationDto = applicationService.getApplication(email, applicationId);

        ModelMapper modelMapper = new ModelMapper();
        ApplicationResponseModel applicationResponseModel = modelMapper.map(applicationDto, ApplicationResponseModel.class);

        return applicationResponseModel;
    }

    @DeleteMapping(path = "/{applicationId}")
    public ApplicationResponseModel deleteApplication(@PathVariable String applicationId) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ApplicationDto applicationDto = applicationService.deleteApplication(email, applicationId);

        ModelMapper modelMapper = new ModelMapper();
        ApplicationResponseModel applicationResponseModel = modelMapper.map(applicationDto, ApplicationResponseModel.class);

        return applicationResponseModel;
    }

    @PostMapping(consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public ApplicationResponseModel createApplication(@RequestBody ApplicationRequestModel applicationRequestModel) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ModelMapper modelMapper = new ModelMapper();
        ApplicationDto applicationDto = modelMapper.map(applicationRequestModel, ApplicationDto.class);

        applicationDto = applicationService.createApplication(email, applicationDto);

        ApplicationResponseModel applicationResponseModel = modelMapper.map(applicationDto, ApplicationResponseModel.class);

        return applicationResponseModel;
    }

    @PatchMapping(path = "/{applicationId}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public ApplicationResponseModel patchApplication(@PathVariable String applicationId, @RequestBody ApplicationRequestModel applicationRequestModel) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ModelMapper modelMapper = new ModelMapper();
        ApplicationDto applicationDto = modelMapper.map(applicationRequestModel, ApplicationDto.class);

        applicationDto = applicationService.patchApplication(email, applicationId, applicationDto);

        ApplicationResponseModel applicationResponseModel = modelMapper.map(applicationDto, ApplicationResponseModel.class);

        return applicationResponseModel;
    }

    @PostMapping(path = "/reset-key", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public void regenerateApplicationKey(@RequestBody ResetApplicationKeyResponseModel resetApplicationKeyResponseModel) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        applicationService.resetApplicationKey(email, resetApplicationKeyResponseModel.getApplicationId());
    }
}
