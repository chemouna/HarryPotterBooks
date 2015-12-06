package com.mounacheikhna.xebiaproject.api.model;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Created by mouna on 06/12/15.
 */
@Root(name = "GoodreadsResponse", strict = false) public class BookResponse {

  @Element(name = "Request") Request request;

  @Element(name = "search", required = false) Search search;

  public Request getRequest() {
    return request;
  }

  public Search getSearch() {
    return search;
  }

  @Nullable
  public GoodreadsBook getFirstBook() {
    if (search == null || search.results == null || search.results.works == null ||
        search.results.works.size() == 0) {
      return null;
    }
    return search.results.works.get(0).mGoodreadsBook;
  }

  @Nullable
  public Work getFirsWork() {
    if (search == null || search.results == null || search.results.works == null ||
        search.results.works.size() == 0) {
      return null;
    }
    return search.results.works.get(0);
  }

  @Root(strict = false) public static class Request {
    @Element(name = "key") String key;

    @Element(name = "method") String method;

    @Element(name = "authentication") String authentication;

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

  @Root(strict = false) public static class Search {
    @Element(name = "results", required = false) Results results;
  }

  @Root(strict = false) public static class Results {
    @ElementList(name = "work", required = false, inline = true) ArrayList<Work> works;
  }

  @Root(strict = false) public static class Work {

    @Element(name = "best_book", required = false) GoodreadsBook mGoodreadsBook;

    @Element(name = "ratings_count", required = false) int ratingsCount;
    @Element(name = "text_reviews_count", required = false) int textReviewsCount;
    @Element(name = "average_rating", required = false) String averageRating;

    @Element(name = "original_publication_year", required = false) int originalPublicationYear;
    @Element(name = "original_publication_month", required = false) int originalPublicationMonth;
    @Element(name = "original_publication_day", required = false) int originalPublicationDay;

    public String getAverageRating() {
      return averageRating;
    }

    public int getRatingsCount() {
      return ratingsCount;
    }

    public int getOriginalPublicationYear() {
      return originalPublicationYear;
    }

    public int getOriginalPublicationMonth() {
      return originalPublicationMonth;
    }

    public int getOriginalPublicationDay() {
      return originalPublicationDay;
    }

    public int getTextReviewsCount() {
      return textReviewsCount;
    }
  }

  @Root(strict = false) public static class GoodreadsBook {

    @Element(name = "id", required = false) String id;
    @Element(name = "title", required = false) String title;
    @Element(name = "isbn", required = false) String isbn;
    @Element(name = "image_url", required = false) String imageUrl;
    @Element(name = "small_image_url", required = false) String smallImageUrl;
    @Element(name = "description", required = false) String description;
    @Element(name = "author", required = false) Author author;

    public String getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    public String getDescription() {
      return description;
    }

    public Author getAuthor() {
      return author;
    }

    public String getImageUrl() {
      return imageUrl;
    }
  }

  @Root(strict = false) public static class Author {

    @Element(name = "name", required = false) String name;

    public String getName() {
      return name;
    }
  }
}
