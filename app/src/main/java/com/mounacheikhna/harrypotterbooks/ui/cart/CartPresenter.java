package com.mounacheikhna.harrypotterbooks.ui.cart;

import android.text.TextUtils;
import com.mounacheikhna.harrypotterbooks.api.harrypotter.HarryPotterBooksApi;
import com.mounacheikhna.harrypotterbooks.api.harrypotter.model.Book;
import com.mounacheikhna.harrypotterbooks.api.harrypotter.model.OfferResponse;
import com.mounacheikhna.harrypotterbooks.ui.base.BasePresenter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by mouna on 05/12/15.
 */
@Singleton public class CartPresenter extends BasePresenter<CartScreen> {

  private HarryPotterBooksApi mApi;

  @Inject public CartPresenter(HarryPotterBooksApi api) {
    mApi = api;
  }

  public Observable<OfferResponse> getOffers(List<Book> books) {
    List<String> listIsbns = new ArrayList<>();
    for (Book b : books) {
      listIsbns.add(b.getIsbn());
    }
    return mApi.fetchCommercialOffers(TextUtils.join(",", listIsbns)).subscribeOn(Schedulers.io());
  }
}
