package com.example.bookmanagement.repository;

import com.example.bookmanagement.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.bookmanagement.model.Book;

import java.util.List;

@Repository
public interface Bookrepository extends JpaRepository<Book , Integer> {

    // saving a book


    /**
     *  here all the save , finsAll() , findById() are all implemented by JPARepository
     */

    /**
     *
     * So what should we do if we need some custom SQL queries instead of inbuilt ?
     * we have @Query() annotation , through which we can write our custom query
     * @Query() - we can write the query in 2 form JPQL & native
     *
     */


    /**
     * 1. JPQL - Java Persistence Query Language   (default format)
     *         - Format in which you write queries keeping Java objects into consideration
     *         - select b from Book b;
     *         - you need to query using an alias
     *         adv
     *         - they are parsed at runtime i.e your sever wont start if your query is wrong
     *         - slightly slows down the app , as the queries get parsed in the run time only
     * 2. Native query
     *         - Format in which you write queries keeping relation / sql table into consideration just like the queries that you write on mysql shell
     *         - select * from book;
     *         - you can either use or ignore alias
     *         disadv
     *         - they are not parsed at runtime i.e your sever starts even if your query is wrong
     *         - in native query you have to explicitly tell about the enum conversion
     */

    // custom sql query
    // JPQL
    @Query("select b from Book b where b.language = :lang")
    public List<Book> findBookByLanguage(Language lang);

    // native
    @Query(value = "select * from book b where b.bookCateogory = :cateo" , nativeQuery = true)
    public List<Book> findBookByCateo(int cateo);

    // JPQL
//    @Query("select b from Book b where b.bookCategory =:bc")
//    @Query("select b from Book b where b.bookCategory = ?1")
//    List<Book> findBooks(BookCategory bc);

    // Native query example
//    @Query(value = "select * from book b where b.bookcategory =:category", nativeQuery = true)
//    @Query(value = "select * from book b where b.bookcategory =?1", nativeQuery = true)
//    List<Book> findBooks(String category);


    // this is a faltu level of abstraction given by the hibernate
//    List<Book> findByBookCategoryAndLanguageAndPagesGreaterThan(BookCategory bc, Language language, int pages);
// any unions and othr complex queries are written by using JPQL or native query



}
