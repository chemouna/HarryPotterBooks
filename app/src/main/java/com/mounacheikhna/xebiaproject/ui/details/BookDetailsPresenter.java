package com.mounacheikhna.xebiaproject.ui.details;

import com.mounacheikhna.xebiaproject.api.goodreads.GoodreadsApi;
import com.mounacheikhna.xebiaproject.api.goodreads.model.GoodreadsResponse;
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

  public Observable<GoodreadsResponse> fetchGoodreadsBook(String isbn) {
    return mGoodreadsApi.getBookByIsbn(isbn).subscribeOn(Schedulers.io());
  }

  public Observable<GoodreadsResponse> searchGoodreadsBook(String title) {
    return mGoodreadsApi.searchBookByTitle(title.replace("Henri Potier", "Harry Potter"))
        .subscribeOn(Schedulers.io());
  }

  public Observable<GoodreadsResponse> showBook(String bookId) {
    return mGoodreadsApi.showBook(bookId).subscribeOn(Schedulers.io());
  }
}
