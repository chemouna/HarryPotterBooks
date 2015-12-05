package com.mounacheikhna.xebiaproject.data;

import com.mounacheikhna.xebiaproject.api.model.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mouna on 05/12/15.
 */
public class Cart {

  List<Book> mBooks = new ArrayList<>();

  public Cart() {}

  public Cart(List<Book> books) {
    mBooks = books;
  }

  public void addBook(Book book) {
    mBooks.add(book);
  }

}
