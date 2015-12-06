package com.mounacheikhna.xebiaproject.api.model;

import java.util.ArrayList;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by mouna on 06/12/15.
 */
@Root(name = "GoodreadsResponse", strict = false)
public class BookResponse {

  @Element(name = "Request")
  Request request;

  @Element(name = "search", required = false)
  Search search;

  public Request getRequest() {
    return request;
  }

  @Root(strict = false)
  public static class Request {
    @Element(name = "key")
    String key;

    @Element(name = "method")
    String method;

    @Element(name = "authentication")
    String authentication;

    public String getKey() {
      return key;
    }

    public String getMethod() {
      return method;
    }

    public String getAuthentication() {
      return authentication;
    }
  }

  @Root(strict = false)
  public static class Search {
    @Element(name = "results", required = false)
    Results results;
  }

  @Root(strict = false)
  public static class Results {
    @ElementList(name="work", required = false, inline = true)
    ArrayList<Work> works;
  }

  @Root(strict = false)
  public static class Work {

    @Element(name="best_book", required = false)
    Book book;
  }

  @Root(strict = false)
  public static class Book {

    @Element(name = "id", required = false)
    String id;

    @Element(name = "title", required = false)
    String title;

    @Element(name = "isbn", required = false)
    String isbn;

    @Element(name = "image_url", required = false)
    String imageUrl;

    @Element(name = "small_image_url", required = false)
    String smallImageUrl;

    @Element(name = "description", required = false)
    String description;

    @Element(name = "average_rating", required = false)
    String averageRating;

    @Element(name = "authors", required = false)
    Authors authors;

    public String getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    public String getIsbn() {
      return isbn;
    }

    public String getImageUrl() {
      return imageUrl;
    }

    public String getSmallImageUrl() {
      return smallImageUrl;
    }

    public String getDescription() {
      return description;
    }

    public String getAverageRating() {
      return averageRating;
    }

    public Authors getAuthors() {
      return authors;
    }
  }

  @Root(strict = false)
  public static class Authors {

    @Element(name = "author", required = false)
    Author author;

    public Author getAuthor() {
      return author;
    }
  }

  @Root(strict = false)
  public static class Author {

    @Element(name = "name", required = false)
    String name;

    public String getName() {
      return name;
    }
  }

}
