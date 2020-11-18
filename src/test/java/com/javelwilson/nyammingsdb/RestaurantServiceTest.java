package com.javelwilson.nyammingsdb;

import com.javelwilson.nyammingsdb.dto.RestaurantDto;
import com.javelwilson.nyammingsdb.entity.RestaurantEntity;
import com.javelwilson.nyammingsdb.repository.RestaurantRepository;
import com.javelwilson.nyammingsdb.service.RestaurantServiceImpl;
import com.javelwilson.nyammingsdb.shared.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class RestaurantServiceTest {

    @InjectMocks
    RestaurantServiceImpl restaurantService;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    Utils utils;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetRestaurant() {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setRestaurantId("abcde");
        restaurantEntity.setName("Island Grill");
        restaurantEntity.setLogo("https://nyammingsdb/s3/placeholder-image");
        restaurantEntity.setDescription("Authentic home cooked Jamaican food.");
        restaurantEntity.setSlogan("Eat Good. Live Good.");

        when(restaurantRepository.findByRestaurantId(anyString())).thenReturn(restaurantEntity);

        RestaurantDto restaurantDto = restaurantService.getRestaurant("abcdefghij");

        assertNotNull(restaurantDto);
        assertEquals("Island Grill", restaurantDto.getName());
    }

    @Test
    public void testGetRestaurant_RuntimeException() {

        when(restaurantRepository.findByRestaurantId(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> restaurantService.getRestaurant("abcdefghij"));
    }

    @Test
    public void testCreateRestaurant() {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setRestaurantId("abcde");
        restaurantEntity.setName("Island Grill");
        restaurantEntity.setLogo("https://nyammingsdb/s3/placeholder-image");
        restaurantEntity.setDescription("Authentic home cooked Jamaican food.");
        restaurantEntity.setSlogan("Eat Good. Live Good.");

        when(restaurantRepository.findByRestaurantId(anyString())).thenReturn(null);
        when(utils.generateRestaurantId(anyInt())).thenReturn("abcdefghij");
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurantEntity);

        RestaurantDto restaurantDto = new RestaurantDto();
        RestaurantDto restaurant = restaurantService.createRestaurant(restaurantDto);

        assertNotNull(restaurant);
        assertEquals("Island Grill", restaurant.getName());
    }

}
