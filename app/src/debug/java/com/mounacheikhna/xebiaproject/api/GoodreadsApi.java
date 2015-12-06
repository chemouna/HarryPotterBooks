package com.mounacheikhna.xebiaproject.api;

import com.mounacheikhna.xebiaproject.BuildConfig;
import com.mounacheikhna.xebiaproject.api.model.BookResponse;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mouna on 06/12/15.
 */
public interface GoodreadsApi {

  //BuildConfig.GOOD_READ_ENDPOINT_URL
  @GET("/book/isbn")
  Observable<BookResponse> getBookByIsbn(@Query("isbn") String isbn);

  @GET("/search")
  Observable<BookResponse> searchBookByTitle(@Query("q") String title);

}
