package com.mindtickle.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ritish on 9/8/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private int bookId;

    private String bookTitle;

    private String author;

    private long purchasedOn;

    private long lastIssued;

    private boolean status;

    private boolean alreadyIssued;

    @JsonIgnore
    private Staff staffAddedTheBook;
}
