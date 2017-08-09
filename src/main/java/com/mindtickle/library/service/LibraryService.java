package com.mindtickle.library.service;

import com.mindtickle.library.dto.Book;
import com.mindtickle.library.dto.IssuedItem;
import com.mindtickle.library.dto.Staff;
import com.mindtickle.library.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by ritish on 9/8/17.
 */
@Service
public class LibraryService {

    private HashMap<Integer, Staff> staffHashMap = new HashMap<>();
    private HashMap<Integer, User> userHashMap = new HashMap<>();
    private HashMap<Integer, Book> bookHashMap = new HashMap<>();
    private TreeMap<String, Book> searchTitleMap = new TreeMap<>();
    private static double fine = 10.5;


    public ResponseEntity<Staff> addStaff(Staff staff) {
        staffHashMap.put(staff.getStaffId(), staff);
        return new ResponseEntity<>(staff, HttpStatus.CREATED);
    }

    public ResponseEntity<User> addUser(User user) {
        userHashMap.put(user.getUserId(), user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    public ResponseEntity<Book> addBook(Book book, int staffId) {
        Staff staff = staffHashMap.get(staffId);
        if (staff == null || !staff.isAdmin()) throw new RuntimeException("You can not add the book");
        book.setStaffAddedTheBook(staff);
        bookHashMap.put(book.getBookId(), book);
        searchTitleMap.put(book.getBookTitle(), book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    public ResponseEntity<Boolean> deleteBook(Integer bookId, int staffId) {
        Staff staff = staffHashMap.get(staffId);
        if (staff == null || !staff.isAdmin()) throw new RuntimeException("You can not add the book");
        Book book = bookHashMap.get(bookId);
        book.setStatus(false);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    public ResponseEntity<Boolean> issueABook(int bookId, int userId) {
        User user = userHashMap.get(userId);
        if (user == null || !user.isStatus() || user.getBookIssued().size() == user.getCategory().getNoOfBooks()){
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
        Book book = bookHashMap.get(bookId);
        if (book == null || !book.isStatus() || book.isAlreadyIssued()){
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
        book.setAlreadyIssued(true);
        long returnDate = System.currentTimeMillis() + user.getCategory().getPeriodToIssue();
        long issuedDate = System.currentTimeMillis();
        IssuedItem issuedItem = new IssuedItem(book, issuedDate, returnDate);
        user.getBookIssued().add(issuedItem);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    public ResponseEntity<Double> returnABook(int bookId, int userId) {
        User user = userHashMap.get(userId);
        if (user == null){
            return new ResponseEntity<Double>(0.0, HttpStatus.OK);
        }

        for (IssuedItem issuedItem : user.getBookIssued()) {
            Book book = issuedItem.getBook();
            if (book.getBookId() == bookId){
                book.setAlreadyIssued(false);
                if (issuedItem.getReturnDate() <= System.currentTimeMillis()) {
                    return new ResponseEntity<Double>(fine, HttpStatus.OK);
                }
                return new ResponseEntity<Double>(0.0, HttpStatus.OK);
            }
        }
        return new ResponseEntity<Double>(0.0, HttpStatus.OK);
    }

    public ResponseEntity<Book> searchBook(String searchText) {
        Set<String> strings = searchTitleMap.keySet();
        ArrayList<String> titles = new ArrayList<>(strings);
        int index = binarySearch(titles, searchText);
        if (index != -1) {
            Book book = searchTitleMap.get(titles.get(index));
            new ResponseEntity<Book>(book, HttpStatus.OK);
        }
        return new ResponseEntity<Book>(new Book(), HttpStatus.OK);
    }

    int binarySearch(ArrayList<String> arr, String str)
    {
        int last = 0, size = arr.size() - 1;
        while (last <= size)
        {
            int m = last + (size-last)/2;
            if (arr.get(m).startsWith(str))
                return m;
            if (arr.get(m).compareTo(str) > 1)
                last = m + 1;
            else
                size = m - 1;
        }
        return -1;
    }

    public ResponseEntity<Boolean> removeUser(int userId, int staffId) {
        Staff staff = staffHashMap.get(staffId);
        if (staff == null || !staff.isAdmin()) throw new RuntimeException("You can not add the book");
        User user = userHashMap.get(userId);
        user.setStatus(false);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
