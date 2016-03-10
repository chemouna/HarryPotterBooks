package com.mounacheikhna.harrypotterbooks.ui.buy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.f2prateek.rx.preferences.Preference;
import com.mounacheikhna.harrypotterbooks.HarryPotterBooksApp;
import com.mounacheikhna.harrypotterbooks.R;
import com.mounacheikhna.harrypotterbooks.api.harrypotter.model.Book;
import com.mounacheikhna.harrypotterbooks.data.Cart;
import com.mounacheikhna.harrypotterbooks.transition.MophFabDialogHelper;
import com.mounacheikhna.harrypotterbooks.ui.view.QuantityView;
import com.mounacheikhna.harrypotterbooks.util.PriceFormatter;
import javax.inject.Inject;
import javax.inject.Named;

import static com.mounacheikhna.harrypotterbooks.util.ApiLevels.isAtLeastLollipop;

/**
 * Created by mouna on 03/12/15.
 *
 * Display a dialog to choose number of books to purchase and confirm.
 * We are using an activity here instead of a dialog to be able to use activity transitions.
 */
public class BuyBook extends AppCompatActivity {

  public static final String EXTRA_BUY_BOOK = "extra_book";
  public static final String EXTRA_ACCENT_COLOR = "extra_accent_color";
  private static final String TAG = "BuyBook";

  @Bind(R.id.container) ViewGroup mContainer;
  @Bind(R.id.book_title) TextView mTitleView;
  @Bind(R.id.book_price) TextView mPriceView;
  @Bind(R.id.confirm) Button mConfirmButton;
  @Bind(R.id.quantity) QuantityView mQuantityView;
  @Bind(R.id.parent) FrameLayout mParent;

  @Inject @Named("cart") Preference<Cart> mCartPref;
  private Book mBook;

  @SuppressLint("NewApi") @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.buy_book);
    ButterKnife.bind(this);

    HarryPotterBooksApp.get(this).getComponent().injectBuyBook(this);

    if (isAtLeastLollipop()) {
      MophFabDialogHelper.setupSharedElementTransitions(this, mContainer,
          getResources().getDimensionPixelSize(R.dimen.dialog_corner));
    }

    mBook = getIntent().getParcelableExtra(EXTRA_BUY_BOOK);
    int accentColor = getIntent().getIntExtra(EXTRA_ACCENT_COLOR, 0);

    if (accentColor != 0) {
      mConfirmButton.setBackgroundColor(accentColor);
      mPriceView.setTextColor(accentColor);
    }
    mTitleView.setText(mBook.getTitle());
    mPriceView.setText(PriceFormatter.formatEuro(mBook.getPrice()));
    mQuantityView.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
      @Override public void onQuantityChange(String key, int quantity) {
        mPriceView.setText(PriceFormatter.formatEuro(mBook.getPrice() * quantity));
      }
    });

    if (isAtLeastLollipop()) {
      TransitionManager.beginDelayedTransition(mContainer);
    }
  }

  @OnClick(R.id.confirm) public void onConfirm() {
    Cart cart = mCartPref.get();
    if (cart == null) cart = new Cart();
    mBook.setQuantity(mQuantityView.getQuantity());
    cart.addBook(mBook);
    mCartPref.set(cart);
    setResult(Activity.RESULT_OK);
    ActivityCompat.finishAfterTransition(this);
  }

  public void dismiss(View view) {
    setResult(Activity.RESULT_CANCELED);
    ActivityCompat.finishAfterTransition(this);
  }

  @Override public void onBackPressed() {
    dismiss(null);
  }

  @OnClick(R.id.parent) public void clickOnParent() {
    dismiss(null);
  }
}
