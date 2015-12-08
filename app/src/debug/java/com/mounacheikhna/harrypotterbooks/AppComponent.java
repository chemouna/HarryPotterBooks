package com.mounacheikhna.harrypotterbooks;

import com.mounacheikhna.harrypotterbooks.api.CoreApiModule;
import com.mounacheikhna.harrypotterbooks.api.DebugApiModule;
import com.mounacheikhna.harrypotterbooks.data.DataModule;
import com.mounacheikhna.harrypotterbooks.ui.book.BookActivity;
import com.mounacheikhna.harrypotterbooks.ui.book.BooksGridView;
import com.mounacheikhna.harrypotterbooks.ui.buy.BuyBook;
import com.mounacheikhna.harrypotterbooks.ui.cart.CartView;
import com.mounacheikhna.harrypotterbooks.ui.details.BookDetailsView;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by cheikhnamouna on 11/21/15.
 *
 * dagger {@link dagger.Component} to inject dependencies into views, activities..
 */
@Singleton @Component(modules = {
    AppModule.class, CoreApiModule.class, DebugApiModule.class, DataModule.class
}) public interface AppComponent {
  void injectApplication(HarryPotterBooksApp starWarsApp);

  void injectBooksView(BooksGridView booksGridView);

  void injectBooksActivity(BookActivity bookActivity);

  void injectBuyBook(BuyBook buyBook);

  void injectCartView(CartView cartView);

  void injectBookDetailsView(BookDetailsView bookDetailsView);

  final class Initializer {
    private Initializer() {
    }

    static AppComponent init(HarryPotterBooksApp app) {
      return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
    }
  }
}
