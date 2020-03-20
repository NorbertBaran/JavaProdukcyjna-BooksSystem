package pl.norbertbaran.books;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {
    final Set<Book> bookSet;

    public BookService(){
        bookSet=new HashSet<>();
        bookSet.add(new Book("123456", "The Lord of the Rings", "J.R.R. Tolkien", Genre.FICTION));
    }

    public boolean add(Book book) {
        return bookSet.add(book);
    }

    public List<Book> getBooks() {
        Set<Book> books=new HashSet<>();
        books.addAll(bookSet);
        books.addAll(getBooksFromOutside());
        return new ArrayList<>(books);
    }

    public List<Book> getBooksFromOutside(){
        RestTemplate restTemplate=new RestTemplate();
        List<Book> outsideBooks=restTemplate.getForObject("https://jwzp-web-app-basic.herokuapp.com/books", ArrayList.class);
        return outsideBooks;
    }

}
