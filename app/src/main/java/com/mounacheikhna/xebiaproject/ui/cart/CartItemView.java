package com.mounacheikhna.xebiaproject.ui.cart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.xebiaproject.R;
import com.mounacheikhna.xebiaproject.api.henripotier.model.Book;
import com.mounacheikhna.xebiaproject.ui.base.BindableBookItem;
import com.mounacheikhna.xebiaproject.util.PriceFormatter;
import com.squareup.picasso.Picasso;

/**
 * Created by mouna on 05/12/15.
 */
public class CartItemView extends LinearLayout implements BindableBookItem {

  @Bind(R.id.cart_item_title) TextView mTitleView;
  @Bind(R.id.cart_item_quantity) TextView mQuantityView;
  @Bind(R.id.cart_item_price) TextView mPriceView;

  public CartItemView(Context context) {
    super(context);
    setOrientation(HORIZONTAL);
  }

  public CartItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setOrientation(HORIZONTAL);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  public void bindTo(Book book, Picasso picasso) {
    mTitleView.setText(book.getTitle());
    mQuantityView.setText(String.valueOf(book.getQuantity()));
    mPriceView.setText(PriceFormatter.formatEuro(book.getQuantity() * book.getPrice()));
  }

  @Override public View getImageView() {
    return null;
  }
}
