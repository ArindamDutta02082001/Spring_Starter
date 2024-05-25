package com.example.project.onlinelibrarywithsecurity.service.libraryService;

import com.example.project.onlinelibrarywithsecurity.models.libraryModels.Book;
import com.example.project.onlinelibrarywithsecurity.models.libraryModels.Student;
import com.example.project.onlinelibrarywithsecurity.models.libraryModels.Transaction;
import com.example.project.onlinelibrarywithsecurity.models.libraryModels.enums.TransactionStatus;
import com.example.project.onlinelibrarywithsecurity.models.libraryModels.enums.TransactionType;
import com.example.project.onlinelibrarywithsecurity.repository.libraryRepository.AuthorRepositiry;
import com.example.project.onlinelibrarywithsecurity.repository.libraryRepository.BookRepository;
import com.example.project.onlinelibrarywithsecurity.repository.libraryRepository.StudentRepository;
import com.example.project.onlinelibrarywithsecurity.repository.libraryRepository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepositiry authorRepositiry;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TransactionRepository transactionRepository;

    // get all transaction from DB
    public List<Transaction> getAllTransactionfromDB()
    {
        return transactionRepository.findAll();
    }

    // Admin to start a new transaction
    public String issueTxn(String bookname, int studentId) throws Exception {

        // validating bookname and studentId
        List<Book> bookList = bookRepository.findByName(bookname);

        if(bookList.isEmpty())
        {
            throw new Exception("Book not found bookname : "+bookname);
        }

        Student student = studentRepository.findById(studentId).orElse(null);

        if(student == null)
        {
            throw new Exception("Student not found : "+studentId);
        }

        if( student.getBookList_S().size() == 3 )
        {
            throw  new Exception("Book limit of 3 reached for the student "+studentId);
        }

        if(bookList.get(0).getMy_student() != null)
        {
            throw new Exception("The Book is already occupied by : "+bookList.get(0).getMy_student().getName());
        }

        //if all validations satisfied then creating a transaction
        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .transactionStatus(TransactionStatus.PENDING)
                .books_in_trans_table(bookList.get(0))
                .students_in_trans_table(student)
                .build();

        try {

            // assign the book to the student who requested for
            bookRepository.assignBookToStudent(bookList.get(0).getId(), student);

            // then saving the successful transaction
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);

        }
        catch(Exception e)
        {
            //  else saving the unsuccessful transaction
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            return "Transaction failed txn_id : "+transaction.getExternalTxnId();
        }
return "Book : "+bookname+" is allocated to student : "+studentId+" with txn_id : "+transaction.getExternalTxnId();
    }

    // Admin to exit a new transaction
    public String returnTxn(String bookname, int studentId) throws Exception {

        // validating bookname and studentId
        List<Book> bookList = bookRepository.findByName(bookname);

        if(bookList == null)
        {
            throw new Exception("Book not found : "+bookname);
        }

        Student student = studentRepository.findById(studentId).orElse(null);

        if(student == null)
        {
            throw new Exception("Student not found : "+studentId);
        }

        // checking if the book is taken by student or just bluffing
        if( bookList.get(0).getMy_student().getId() != studentId )
        {
            throw new Exception("The Book "+bookname+" is not taken by this student "+studentId);
        }

        // calculating the fine to be imposed if book is retained by more than 7days
        // -to-do-

        //if all validations satisfied then creating a transaction
        Transaction transaction = Transaction.builder().externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .transactionStatus(TransactionStatus.PENDING)
                .books_in_trans_table(bookList.get(0))
                .students_in_trans_table(student)
                .build();

        try {

            // unassigned the book from the student
            bookRepository.unassignBookToStudent(bookList.get(0).getId());

            // then saving the successful transaction
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);
        }
        catch(Exception e)
        {
            //  else saving the unsuccessful transaction
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            return "Transaction failed txn_id : "+transaction.getExternalTxnId();

        }

        return "Book : "+bookname+" is de-allocated from student : "+studentId+" with txn_id : "+transaction.getExternalTxnId();

    }
}
