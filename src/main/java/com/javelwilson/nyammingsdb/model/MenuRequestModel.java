package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import java.util.List;

@Data
public class MenuRequestModel {
    private String name;

    private String url;

    private List<MenuSectionRequestModel> menuSections;
}
