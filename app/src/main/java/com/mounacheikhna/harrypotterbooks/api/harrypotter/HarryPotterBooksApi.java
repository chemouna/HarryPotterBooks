package com.mounacheikhna.harrypotterbooks.api.harrypotter;

import com.mounacheikhna.harrypotterbooks.api.harrypotter.model.Book;
import com.mounacheikhna.harrypotterbooks.api.harrypotter.model.OfferResponse;
import java.util.List;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by mouna on 02/12/15.
 *
 * An interface for HarryPotterBooks api.
 */
public interface HarryPotterBooksApi {

  @GET("/books") Observable<List<Book>> fetchBooks();

  @GET("/books/{listisbn}/commercialOffers") Observable<OfferResponse> fetchCommercialOffers(
      @Path("listisbn") String isbns);
}
