package com.mindtickle.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ritish on 9/8/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCategory {

    private int noOfBooks;

    private long periodToIssue;
}
