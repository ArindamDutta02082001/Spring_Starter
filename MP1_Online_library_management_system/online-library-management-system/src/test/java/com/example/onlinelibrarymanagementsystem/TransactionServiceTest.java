package com.example.onlinelibrarymanagementsystem;

import com.example.onlinelibrarymanagementsystem.models.Book;
import com.example.onlinelibrarymanagementsystem.models.Student;
import com.example.onlinelibrarymanagementsystem.models.Transaction;
import com.example.onlinelibrarymanagementsystem.models.enums.Genre;
import com.example.onlinelibrarymanagementsystem.models.enums.TransactionStatus;
import com.example.onlinelibrarymanagementsystem.models.enums.TransactionType;
import com.example.onlinelibrarymanagementsystem.repository.BookRepository;
import com.example.onlinelibrarymanagementsystem.repository.StudentRepository;
import com.example.onlinelibrarymanagementsystem.repository.TransactionRepository;
import com.example.onlinelibrarymanagementsystem.service.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    BookRepository bookRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    TransactionRepository transactionRepository;


    @Test
    public void happyCase_issuetxn_test() throws Exception {
        String bookname = "ABC";
        int studentId = 1;
        String externalTxnIdExpected = "3daf979b-ae2c-4e0e-9fbf-d8dc27bd612e";

        List<Book> bookList = new ArrayList<>();
        bookList.add( Book.builder()
                .name("ABC")
                .pages(5000)
                .genre(Genre.FICTION)
                .build());

        Student student = Student.builder()
                        .id(1)
                        .name("jagga")
                        .contact("12345")
                        .bookList_S(bookList)
                        .build();

        Transaction transaction = Transaction.builder()
                .externalTxnId(externalTxnIdExpected)
                .transactionType(TransactionType.ISSUE)
                .transactionStatus(TransactionStatus.SUCCESS)
                .books_in_trans_table(bookList.get(0))
                .students_in_trans_table(student)
                .build();




        Mockito.when(bookRepository.findByName(bookname)).thenReturn(bookList);
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.ofNullable(student));
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        String actualExtenalTxnId  = transactionService.issueTxn(bookname , studentId);

//        Assert.assertEquals(externalTxnIdExpected , actualExtenalTxnId);
            Assert.assertNotNull(actualExtenalTxnId);



    }

    @Test(expected = Exception.class)
    public void book_empty_issuetxn_test() throws Exception {
        String bookname = "ABC";
        int studentId = 1 ;
        List<Book> bookList = new ArrayList<>();
        Student student= null;
        Mockito.when(bookRepository.findByName(bookname)).thenReturn(bookList);
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.ofNullable(student));
        String txnIdReturned = transactionService.issueTxn(bookname, studentId);
    }

    @Test(expected = Exception.class)
    public void student_notfound_issuetxn_test() throws Exception {
        String bookname = "ABC";
        int studentId = 1 ;

        List<Book> bookList = new ArrayList<>();
        bookList.add( Book.builder()
                .name("ABC")
                .pages(5000)
                .genre(Genre.FICTION)
                .build());

        Student student= null;

        Mockito.when(bookRepository.findByName(bookname)).thenReturn(bookList);
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.ofNullable(student));
        String txnIdReturned = transactionService.issueTxn(bookname, studentId);
    }

    @Test(expected = Exception.class)
    public void student_booklimit_issuetxn_test() throws Exception {
        String bookname = "ABC";
        int studentId = 1 ;

        List<Book> bookList = new ArrayList<>();
        bookList.add( Book.builder()
                .name("ABC")
                .pages(5000)
                .genre(Genre.FICTION)
                .build()
        );
        bookList.add( Book.builder()
                .name("ABC")
                .pages(5000)
                .genre(Genre.FICTION)
                .build()
        );
        bookList.add( Book.builder()
                .name("ABC")
                .pages(5000)
                .genre(Genre.FICTION)
                .build()
        );

        Student student= null;

        Mockito.when(bookRepository.findByName(bookname)).thenReturn(bookList);
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.ofNullable(student));
        String txnIdReturned = transactionService.issueTxn(bookname, studentId);
    }


    @Test(expected = Exception.class)
    public void book_already_occupied_issuetxn_test() throws Exception {
        String bookname = "ABC";
        int studentId = 1 ;

        Student student = Student.builder()
                .id(1)
                .name("jagga")
                .contact("12345")
                .build();

        List<Book> bookList = new ArrayList<>();
        bookList.add( Book.builder()
                .name("ABC")
                .pages(5000)
                .genre(Genre.FICTION)
                .my_student(student)
                .build() );

        Mockito.when(bookRepository.findByName(bookname)).thenReturn(bookList);
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.ofNullable(student));
        Mockito.when(bookList.get(0).getMy_student()).thenReturn(student);
        String txnIdReturned = transactionService.issueTxn(bookname, studentId);
//        Assert.assertNotNull(bookList.get(0).getMy_student());
    }
}
