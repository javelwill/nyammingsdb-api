package com.javelwilson.nyammingsdb.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Home", tags = {"Home"}, description = "REST API for Home")
public interface HomeController {
    @ApiOperation("get home")
    String home();
}
