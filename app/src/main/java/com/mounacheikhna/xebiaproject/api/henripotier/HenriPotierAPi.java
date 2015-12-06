package com.mounacheikhna.xebiaproject.api.henripotier;

import com.mounacheikhna.xebiaproject.api.henripotier.model.Book;
import com.mounacheikhna.xebiaproject.api.henripotier.model.OfferResponse;
import java.util.List;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by mouna on 02/12/15.
 */
public interface HenriPotierAPi {

  @GET("/books") Observable<List<Book>> fetchBooks();

  @GET("/books/{listisbn}/commercialOffers") Observable<OfferResponse> fetchCommercialOffers(
      @Path("listisbn") String isbns);
}
