package com.nguyen.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String type;
}
