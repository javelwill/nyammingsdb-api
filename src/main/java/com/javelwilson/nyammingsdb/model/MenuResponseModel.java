package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import java.util.List;

@Data
public class MenuResponseModel {
    private String menuId;

    private String name;

    private String url;

    private List<MenuSectionResponseModel> menuSections;
}
