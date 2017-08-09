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
public class Staff {
    private int staffId;

    private int staffName;

    private boolean isAdmin;
}
