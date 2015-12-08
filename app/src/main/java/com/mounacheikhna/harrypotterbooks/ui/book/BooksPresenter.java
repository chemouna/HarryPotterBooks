package com.mounacheikhna.harrypotterbooks.ui.book;

import com.mounacheikhna.harrypotterbooks.api.harrypotter.HarryPotterBooksApi;
import com.mounacheikhna.harrypotterbooks.api.harrypotter.model.Book;
import com.mounacheikhna.harrypotterbooks.ui.base.BasePresenter;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

/**
 * Created by mouna on 02/12/15.
 */
@Singleton // singleton for now -> TODO: make it later scoped per component
public class BooksPresenter extends BasePresenter<BooksScreen> {

  private HarryPotterBooksApi mApi;

  @Inject public BooksPresenter(HarryPotterBooksApi api) {
    mApi = api;
  }

  public Observable<List<Book>> loadBooks() {
    return mApi.fetchBooks();
  }
}
