package com.mounacheikhna.xebiaproject.api.goodreads;

import com.mounacheikhna.xebiaproject.api.goodreads.model.GoodreadsResponse;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mouna on 06/12/15.
 */
public interface GoodreadsApi {

  @GET("/book/isbn") Observable<GoodreadsResponse> getBookByIsbn(@Query("isbn") String isbn);

  @GET("/search") Observable<GoodreadsResponse> searchBookByTitle(@Query("q") String title);

  @GET("/book/show") Observable<GoodreadsResponse> showBook(@Query("id") String bookId);
}
