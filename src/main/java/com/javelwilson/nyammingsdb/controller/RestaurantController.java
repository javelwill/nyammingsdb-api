package com.javelwilson.nyammingsdb.controller;

import com.javelwilson.nyammingsdb.model.*;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "Restaurants", tags = {"Restaurants"}, description = "REST API for Restaurants")
@Validated
public interface RestaurantController {


    @GetMapping(path = "/api/restaurants", produces = {APPLICATION_JSON_VALUE})
    List<RestaurantResponseModel> getRestaurants(@RequestParam(value = "page", defaultValue = "0")
                                                         int page,
                                                 @RequestParam(value = "limit", defaultValue = "10")
                                                 @Min(1) @Max(50)
                                                         int limit);

    @PostMapping(path="/restaurants", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    RestaurantResponseModel createRestaurant(@Valid @RequestBody RestaurantRequestModel restaurantRequestModel);

    @PatchMapping(path = "/restaurants/{id}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    RestaurantResponseModel patchRestaurant(@PathVariable String id, @RequestBody RestaurantRequestModel restaurantRequestModel);

    @GetMapping(path = "/api/restaurants/{id}", produces = {APPLICATION_JSON_VALUE})
    RestaurantResponseModel getRestaurant(@PathVariable String id);

    @DeleteMapping(path = "/restaurants/{id}", produces = {APPLICATION_JSON_VALUE})
    RestaurantResponseModel deleteRestaurant(@PathVariable String id);

    @PostMapping(path = "/restaurants/{id}/locations", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    LocationResponseModel addLocation(@PathVariable String id, @RequestBody LocationRequestModel locationRequestModel);

    @GetMapping(path = "/api/restaurants/{id}/locations", produces = {APPLICATION_JSON_VALUE})
    List<LocationResponseModel> getRestaurantLocations(@PathVariable String id);

    @GetMapping(path = "/api/restaurants/{restaurantId}/locations/{locationId}", produces = {APPLICATION_JSON_VALUE})
    LocationResponseModel getRestaurantLocation(@PathVariable String restaurantId, @PathVariable String locationId);

    @PostMapping(path = "/restaurants/{id}/menus", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    MenuResponseModel addMenu(@PathVariable String id, @RequestBody MenuRequestModel menuRequestModel);

    @GetMapping(path = "/api/restaurants/{id}/menus", produces = {APPLICATION_JSON_VALUE})
    List<MenuResponseModel> getRestaurantMenus(@PathVariable String id);

    @GetMapping(path = "/api/restaurants/{restaurantId}/menus/{menuId}", produces = {APPLICATION_JSON_VALUE})
    MenuResponseModel getRestaurantMenu(@PathVariable String restaurantId, @PathVariable String menuId);
}
