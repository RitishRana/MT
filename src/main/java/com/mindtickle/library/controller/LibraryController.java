package com.mindtickle.library.controller;

import com.mindtickle.library.dto.Book;
import com.mindtickle.library.dto.Staff;
import com.mindtickle.library.dto.User;
import com.mindtickle.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ritish on 9/8/17.
 */
@Controller
@RequestMapping(value = "/api/v1/mt")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @RequestMapping(value = "/addStaff", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Staff> addStaff(@RequestBody Staff staff) {
        return libraryService.addStaff(staff);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return libraryService.addUser(user);
    }

    @RequestMapping(value = "/users/{userId}/remove/{staffId}", method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<Boolean> removeUser(@PathVariable("userId") int userId,  @PathVariable("staffId") int staffId) {
        return libraryService.removeUser(userId, staffId);
    }

    @RequestMapping(value = "/books/add/{staffId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Book> addBook(@RequestBody Book book,  @PathVariable("staffId") int staffId) {
        return libraryService.addBook(book, staffId);
    }

    @RequestMapping(value = "/books/{bookId}/remove/{staffId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Boolean> deleteBook(@PathVariable("bookId") int bookId, @PathVariable("staffId") int staffId) {
        return libraryService.deleteBook(bookId, staffId);
    }

    @RequestMapping(value = "/books/{bookId}/issue/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Boolean> issueABook(@PathVariable("bookId") int bookId, @PathVariable("userId") int userId) {
        return libraryService.issueABook(bookId, userId);
    }

    @RequestMapping(value = "/books/{bookId}/return/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Double> returnABook(@PathVariable("staffId") int bookId, @PathVariable("userId") int userId) {
        return libraryService.returnABook(bookId, userId);
    }

    @RequestMapping(value = "/books/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Book> searchBook(@RequestParam(value = "query") String searchText) {
        return libraryService.searchBook(searchText);
    }



}
