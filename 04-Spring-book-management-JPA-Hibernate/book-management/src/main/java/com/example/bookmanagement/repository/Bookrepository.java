package com.example.bookmanagement.repository;

import com.example.bookmanagement.model.enums.Cateogory;
import com.example.bookmanagement.model.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
     *         - you need to query using an alias ( select b from Book b; )
     *
     *         - they are parsed at runtime i.e your sever wont start if your query is wrong
     *         - slightly slows down the app
     *
     * 2. Native query
     *         - Format in which you write queries keeping relation / sql table into consideration just like the queries that you write on mysql shell
     *         - you can either use or ignore alias ( select * from book; )
     *
     *         - they are not parsed at runtime i.e your sever starts even if your query is wrong
     *         - ** in native query you have to explicitly tell about the enum
     */

    // custom sql query

    // to find a book by language

    // JPQL
    @Query("select b from Book b where b.language = :lang")
    public List<Book> findBookByLanguage(Language lang);

    // native
    // @Query(value = "select * from book b where b.language = :lang" , nativeQuery = true)
    // public List<Book> findBookByLanguage(int lang);

    // to find a book by cateogory

    // JPQL
    // @Query( "select b from Book b where b.bookCategory = :cateo" )
    // public List<Book> findBookByCateo( Cateogory cateo );

    // native
    @Query(value = "select * from book b where b.book_cateogory = :cateo" , nativeQuery = true)
    public List<Book> findBookByCateo(String cateo);





    // this is a faltu level of abstraction given by the hibernate
//    List<Book> findByBookCategoryAndLanguageAndPagesGreaterThan(BookCategory bc, Language language, int pages);
// any unions and othr complex queries are written by using JPQL or native query




    @Modifying
    @Query("update Book b set b.name = ?1 , b.authorName = ?2 , b.publisherName = ?3 , b.pages = ?4 , b.bookCateogory = ?5 , b.language = ?6 where id = ?7 ")
    public void updateBook(String name , String authorName , String publisherName , String pages , Cateogory bookCateogory , Language language, Integer bookId);



}
