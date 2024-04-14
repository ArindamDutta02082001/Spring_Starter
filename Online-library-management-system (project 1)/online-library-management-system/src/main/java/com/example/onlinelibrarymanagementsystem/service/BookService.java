package com.example.onlinelibrarymanagementsystem.service;

import com.example.onlinelibrarymanagementsystem.dto.CreateAuthorDto;
import com.example.onlinelibrarymanagementsystem.dto.CreateBookDto;
import com.example.onlinelibrarymanagementsystem.dto.CreateStudentDto;
import com.example.onlinelibrarymanagementsystem.dto.SearchBookdto;
import com.example.onlinelibrarymanagementsystem.models.Author;
import com.example.onlinelibrarymanagementsystem.models.Book;
import com.example.onlinelibrarymanagementsystem.models.Student;
import com.example.onlinelibrarymanagementsystem.models.enums.Genre;
import com.example.onlinelibrarymanagementsystem.repository.AuthorRepositiry;
import com.example.onlinelibrarymanagementsystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    AuthorRepositiry authorRepositiry;


    // creating a new Book Object from Book DTO using builder
    private Book createBook(CreateBookDto bookDto)
    {
        return  Book.builder()
                .name(bookDto.getName())
                .genre(bookDto.getGenre())
                .pages(bookDto.getPages())
                .build();
    }

    // to save a book upo a POST call
    public Book saveBook(CreateBookDto bookDto)
    {
        Book newBook = createBook(bookDto);

        ///////////// to fill the author attribute in the object created ////////////
        Author author = authorService.getAuthorByEmail(bookDto.getAuthor().getEmail());
        if(author == null )
        {
            CreateAuthorDto authorDto = new CreateAuthorDto(bookDto.getAuthor().getEmail() , bookDto.getAuthor().getAuthorName() , bookDto.getAuthor().getCountry());
            author = authorService.saveAuthor(authorDto);
        }

        newBook.setAuthored_by(author);
        bookRepository.save(newBook);

        return newBook;
    }


    // to get all the books upon a GET call
    public List<Book> getAllBooks()
    {
        return bookRepository.findAll();
    }

    // to get a book by id upon a GET call
    public Book getBookById( Integer bookId)
    {
        return bookRepository.findById(bookId).orElse(null);
    }

    // to delete a book by id upon a DELETE call
    public Book deleteBookId( Integer bookId)
    {
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book != null)
        {
            bookRepository.deleteById(bookId);
        }
        return book;
    }

    // to update a book upon a PUT call     to-do
    public Book updateNewBook( CreateBookDto request , Integer bookId )
    {
        // method 1 -
        Book newBook = bookRepository.findById(bookId).orElse(null);

        if(newBook == null)
            return null;


        // if author exists well and good if not create a new author and link its id
        Author author = authorService.getAuthorByEmail(request.getAuthor().getEmail());
        if( author == null )
        {
           CreateAuthorDto authorDto = new CreateAuthorDto(request.getAuthor().getEmail() , request.getAuthor().getAuthorName() , request.getAuthor().getCountry());
           authorService.saveAuthor(authorDto);
            author = authorService.getAuthorByEmail(request.getAuthor().getEmail());
        }

        newBook.setAuthored_by(author);
        bookRepository.save(newBook);

        return newBook;

        // Method 2 - without using save() | by custom query

//        Book book = createBook(request);
//        book.setId(bookId);
//
//        // if author exists well and good if not create a new author and link its id
//        Author author = authorService.getAuthorByEmail(request.getAuthor().getEmail());
//        if( author == null )
//        {
//            CreateAuthorDto createAuthorDto = new CreateAuthorDto(request.getAuthor().getEmail(), request.getAuthor().getAuthorName(), request.getAuthor().getCountry());
//            authorService.saveAuthor(createAuthorDto);
//            author = authorService.getAuthorByEmail(request.getAuthor().getEmail());
//        }
//        else
//        {
//            // update the particular author
//            Author newAuthor = new Author(author.getId(), request.getAuthor().getEmail(), request.getAuthor().getAuthorName(), request.getAuthor().getCountry(),author.getAddedOn(),author.getBookList());
//            authorRepositiry.save(newAuthor);
//        }
//
//        bookRepository.updateBook(request.getName(), request.getGenre(),request.getPages() , bookId, author.getId());
//        return book;

    }

    /* ******************** to implement search functionality *********************** */
    private boolean available;

    // to hardcode the keys that are allowed
    private static Set<String> allowedKeys = new HashSet<>();
    // to specify the operations
    private static Map<String, List<String>> allowedOperatorsMap = new HashMap<>();

    BookService(){
        allowedKeys.addAll(Arrays.asList("name", "author_name", "genre", "pages", "id"));

        allowedOperatorsMap.put("name", Arrays.asList("=", "like"));
        allowedOperatorsMap.put("author_name", Arrays.asList("="));
        allowedOperatorsMap.put("pages", Arrays.asList("<", "<=", ">", ">=", "="));
        allowedOperatorsMap.put("genre", Arrays.asList("="));
        allowedOperatorsMap.put("id", Arrays.asList("="));
    }
//
//
//    public boolean validate(){
//
//        if(!allowedKeys.contains(this.searchKey)){
//            return false;
//        }
//
//
//        List<String> validOperators = allowedOperatorsMap.get(this.searchKey);
//        if(!validOperators.contains(this.operator)){
//            return false;
//        }
//
//        return true;
//    }


    public List<Book> search(SearchBookdto searchBookRequest) throws Exception {
//        boolean isValidRequest = searchBookRequest.validate();
//        if(!isValidRequest){
//            throw new Exception("Invalid Request");
//        }

//        String sql = "select b from Book b where b.searchKey searchOperator searchValue";

        switch (searchBookRequest.getSearchKey()){
            case "name":
                return bookRepository.findByName(searchBookRequest.getSearchValue());
            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(searchBookRequest.getSearchValue()));
            case "id": {
                Book book = bookRepository.findById(Integer.parseInt(searchBookRequest.getSearchValue())).orElse(null);
                return Arrays.asList(book);
            }

            default:
                throw new Exception("invalid search key");
        }


    }

}
