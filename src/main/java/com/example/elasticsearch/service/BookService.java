package com.example.elasticsearch.service;

import com.example.elasticsearch.domain.Book;

import com.example.elasticsearch.utils.elasticsearch.SearchQueryBuilders;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.elasticsearch.domain.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class BookService {

    @Autowired
    private BookRepositary bookRepositary;

    @Autowired
    SearchQueryBuilders searchQueryBuilders;

    public String insertBook(Book book){
        bookRepositary.save(book);
        return "successfully insert";
    }

    //getting

    public List<Book> getAllBook(){

        Iterable<Book> itr=bookRepositary.findAll();
        List<Book> lsBook=new ArrayList<>();
        itr.forEach(lsBook::add);
        if(lsBook.size()==0){
            log.info("Sorry boss value is empty");
        }
        log.info("Getting Book object"+lsBook);
        return lsBook;
    }


    public void deleteCurrentRow(String id) {
        bookRepositary.deleteById(id);
    }

    public Book getByAuthor(String author) {
        return bookRepositary.findByAuthor(author);
    }

    public void deleteAllValue() {
        bookRepositary.deleteAll();
    }


    public List<Book> findAllTitleOnly(String word) {
        return    searchQueryBuilders.getOnlyTitle( word );


    }
}
