package com.mounacheikhna.xebiaproject.ui.buy;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mounacheikhna.xebiaproject.R;
import com.mounacheikhna.xebiaproject.api.model.Book;
import com.mounacheikhna.xebiaproject.transition.MophFabDialogHelper;
import com.mounacheikhna.xebiaproject.ui.view.QuantityView;
import com.mounacheikhna.xebiaproject.util.PriceFormatter;

import static com.mounacheikhna.xebiaproject.util.ApiLevels.isAtLeastLollipop;

/**
 * Created by mouna on 03/12/15.
 *
 * We are using an activity here instead of a dialog to be able to use activity transitions.
 */
 public class BuyBook extends AppCompatActivity {

  public static final String EXTRA_BUY_BOOK = "extra_book";
  public static final String EXTRA_ACCENT_COLOR = "extra_accent_color";

  @Bind(R.id.container) ViewGroup mContainer;
  @Bind(R.id.book_title) TextView mTitleView;
  @Bind(R.id.book_price) TextView mPriceView;
  @Bind(R.id.confirm) Button mConfirmButton;
  @Bind(R.id.quantity) QuantityView mQuantityView;

  @SuppressLint("NewApi") @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.buy_book);
    ButterKnife.bind(this);

    getWindow().getDecorView()
        .setBackgroundColor(ContextCompat.getColor(this, R.color.background_dark));

    if (isAtLeastLollipop()) {
      MophFabDialogHelper.setupSharedElementTransitions(this, mContainer,
          getResources().getDimensionPixelSize(R.dimen.dialog_corner));
    }

    final Book book = getIntent().getParcelableExtra(EXTRA_BUY_BOOK);
    int accentColor = getIntent().getIntExtra(EXTRA_ACCENT_COLOR, 0);

    if(accentColor != 0) {
      mConfirmButton.setBackgroundColor(accentColor);
      mPriceView.setTextColor(accentColor);
    }
    mTitleView.setText(book.getTitle());
    mPriceView.setText(PriceFormatter.formatEuro(book.getPrice()));
    mQuantityView.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
      @Override public void onQuantityChange(String key, int quantity) {
        mPriceView.setText(PriceFormatter.formatEuro(book.getPrice() * quantity));
      }
    });
  }

  @OnClick(R.id.confirm)
  public void onConfirm() {
    setResult(Activity.RESULT_OK);
    ActivityCompat.finishAfterTransition(this);
  }

}
