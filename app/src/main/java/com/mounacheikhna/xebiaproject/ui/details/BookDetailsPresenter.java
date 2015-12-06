package com.mounacheikhna.xebiaproject.ui.details;

import com.mounacheikhna.xebiaproject.api.GoodreadsApi;
import com.mounacheikhna.xebiaproject.api.model.BookResponse;
import com.mounacheikhna.xebiaproject.ui.base.BasePresenter;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by mouna on 06/12/15.
 */
@Singleton public class BookDetailsPresenter extends BasePresenter<BookDetailsScreen> {

  private GoodreadsApi mGoodreadsApi;

  @Inject public BookDetailsPresenter(GoodreadsApi goodreadsApi) {
    mGoodreadsApi = goodreadsApi;
  }

  public Observable<BookResponse> fetchGoodreadsBook(String isbn) {
    return mGoodreadsApi.getBookByIsbn(isbn).subscribeOn(Schedulers.io());
  }

  public Observable<BookResponse> searchGoodreadsBook(String title) {
    return mGoodreadsApi.searchBookByTitle(title.replace("Henri Potier", "Harry Potter"))
        .subscribeOn(Schedulers.io());
  }
}
