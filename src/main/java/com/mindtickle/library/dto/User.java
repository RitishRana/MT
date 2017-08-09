package com.mindtickle.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritish on 9/8/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int userId;

    private int userName;

    private String dob;

    private boolean status;

    private List<IssuedItem> bookIssued = new ArrayList<>();

    private UserCategory category;

}
