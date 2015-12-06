package com.mounacheikhna.xebiaproject.ui.cart;

import android.text.TextUtils;
import com.mounacheikhna.xebiaproject.api.henripotier.HenriPotierAPi;
import com.mounacheikhna.xebiaproject.api.henripotier.model.Book;
import com.mounacheikhna.xebiaproject.api.henripotier.model.OfferResponse;
import com.mounacheikhna.xebiaproject.ui.base.BasePresenter;
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

  private HenriPotierAPi mApi;

  @Inject public CartPresenter(HenriPotierAPi api) {
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
