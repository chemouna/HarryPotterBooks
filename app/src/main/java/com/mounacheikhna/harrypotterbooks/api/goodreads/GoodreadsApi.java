package com.mounacheikhna.harrypotterbooks.api.goodreads;

import com.mounacheikhna.harrypotterbooks.api.goodreads.model.GoodreadsResponse;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mouna on 06/12/15.
 *
 * An interface to Goodreads site api to get more information about books.
 */
public interface GoodreadsApi {

  @GET("/book/isbn") Observable<GoodreadsResponse> getBookByIsbn(@Query("isbn") String isbn);

  @GET("/search") Observable<GoodreadsResponse> searchBookByTitle(@Query("q") String title);

  @GET("/book/show") Observable<GoodreadsResponse> showBook(@Query("id") String bookId);
}
