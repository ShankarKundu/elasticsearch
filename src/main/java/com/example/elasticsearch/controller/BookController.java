package com.example.elasticsearch.controller;

import com.example.elasticsearch.service.BookService;
import com.example.elasticsearch.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sun.activation.registries.LogSupport.log;

@RestController
@Slf4j
public class BookController {
    @Autowired
    BookService bookService;


    @PostMapping("/book")
    public String insertBook(@RequestBody Book book) throws Exception {
        BookController.log.info("Inserted Book Object");
        return bookService.insertBook(book);
    }

   @GetMapping("/book")
    public List<Book> gellListOfBook(){

        return bookService.getAllBook();
   }

   @DeleteMapping("/book/{id}")
    public void deleteById(@PathVariable String id){
       BookController.log.info("Delete Specific book Object");
        bookService.deleteCurrentRow(id);

   }
   @GetMapping("/book/name/{author}")
    public Book getByName(@PathVariable String author){
        return bookService.getByAuthor(author);
   }

   @DeleteMapping("/book/deleteAll")
    public void deleteAll(){
        bookService.deleteAllValue();

   }

   @GetMapping("book/{word}")
    public List<Book> searchByWord(@PathVariable String word){
        System.out.println(  "{nnnnnnnnnnnnnnnn}");
        List<Book> lstBook= bookService.findAllTitleOnly(word);
        log(" nbg"+lstBook);
        return lstBook;
   }





}
