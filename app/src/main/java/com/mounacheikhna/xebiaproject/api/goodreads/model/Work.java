package com.mounacheikhna.xebiaproject.api.goodreads.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by mouna on 06/12/15.
 */
@Root(strict = false) public class Work {

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

  public CharSequence getFormattedReleaseDate() {
    return getOriginalPublicationDay()
        + "/"
        + getOriginalPublicationMonth()
        + "/"
        + getOriginalPublicationYear();
  }
}