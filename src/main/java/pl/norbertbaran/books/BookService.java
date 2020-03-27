package pl.norbertbaran.books;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class BookService {
    final Set<Book> bookSet;

    public BookService(){
        bookSet=new HashSet<>();
        bookSet.add(new Book("101", "The Shawshank Redemption", " Frank Darabont", Genre.FICTION));
        bookSet.add(new Book("102", "The Godfather", "Francis Ford Coppola", Genre.FICTION));
    }

    public boolean add(Book book) {
        Set<Book> books = new HashSet<>(bookSet);
        books.addAll(getBooksFromOutside("https://jwzp-web-app-basic.herokuapp.com/books"));
        books.addAll(getBooksFromOutside("https://cascade-request.herokuapp.com/books"));
        if(!books.contains(book))
            return bookSet.add(book);
        return false;
    }

    public List<Book> getBooks() {
        Set<Book> books = new HashSet<>(bookSet);
        books.addAll(getBooksFromOutside("https://jwzp-web-app-basic.herokuapp.com/books"));
        books.addAll(getBooksFromOutside("https://cascade-request.herokuapp.com/books"));
        return new ArrayList<>(books);
    }

    public List<Book> getBooksFromOutside(String url){
        Book[] books=null;
        try {
            books = new RestTemplate().getForEntity(url, Book[].class).getBody();
        }catch (HttpClientErrorException e){
            System.err.println("Getting books from \""+url+"\". Error: "+e.getStatusCode());
        }catch(IllegalArgumentException e){
            System.err.println("Getting books from \""+url+"\". Bad url.");
        }
        return books==null ? List.of() : Arrays.asList(books);
    }

}
