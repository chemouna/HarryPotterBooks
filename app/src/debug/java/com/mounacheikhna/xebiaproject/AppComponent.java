package com.mounacheikhna.xebiaproject;

import com.mounacheikhna.xebiaproject.api.CoreApiModule;
import com.mounacheikhna.xebiaproject.api.DebugApiModule;
import com.mounacheikhna.xebiaproject.data.DataModule;
import com.mounacheikhna.xebiaproject.ui.book.BookActivity;
import com.mounacheikhna.xebiaproject.ui.book.BooksGridView;
import com.mounacheikhna.xebiaproject.ui.buy.BuyBook;
import com.mounacheikhna.xebiaproject.ui.cart.CartView;
import com.mounacheikhna.xebiaproject.ui.details.BookDetailsView;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
@Singleton @Component(modules = {
    AppModule.class, CoreApiModule.class, DebugApiModule.class, DataModule.class
}) public interface AppComponent {
  void injectApplication(HenriPotierApp starWarsApp);

  void injectBooksView(BooksGridView booksGridView);

  void injectBooksActivity(BookActivity bookActivity);

  void injectBuyBook(BuyBook buyBook);

  void injectCartView(CartView cartView);

  void injectBookDetailsView(BookDetailsView bookDetailsView);

  final class Initializer {
    private Initializer() {
    }

    static AppComponent init(HenriPotierApp app) {
      return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
    }
  }
}
