package com.mounacheikhna.xebiaproject.ui.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.f2prateek.rx.preferences.Preference;
import com.mounacheikhna.xebiaproject.HenriPotierApp;
import com.mounacheikhna.xebiaproject.R;
import com.mounacheikhna.xebiaproject.data.Cart;
import com.mounacheikhna.xebiaproject.ui.book.BooksAdapter;
import com.mounacheikhna.xebiaproject.ui.view.CustomViewAnimator;
import com.mounacheikhna.xebiaproject.ui.view.recyclerview.HeaderFooterAdapter;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by mouna on 05/12/15.
 */
public class CartView extends FrameLayout implements CartScreen {

  @Bind(R.id.rv) RecyclerView mBooksView;
  @Bind(R.id.container_animator) CustomViewAnimator mAnimatorView;
  @Bind(R.id.state) TextView mStateView;

  @Inject @Named("cart") Preference<Cart> mCartPref;
  @Inject Picasso mPicasso;

  private HeaderFooterAdapter<BooksAdapter> mHeaderFooterAdapter;

  public CartView(Context context) {
    super(context);
    init(context);
  }

  public CartView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public CartView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    if (isInEditMode()) return;
    final View view = LayoutInflater.from(context).inflate(R.layout.cart_view, this, true);
    ButterKnife.bind(this, view);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    HenriPotierApp.get(getContext()).getComponent().injectCartView(this);

    BooksAdapter adapter = new BooksAdapter(R.layout.cart_book_item, mPicasso);
    mHeaderFooterAdapter = new HeaderFooterAdapter<>(adapter);
    final View cartHeaderView =
        LayoutInflater.from(getContext()).inflate(R.layout.cart_header, this, false);
    mHeaderFooterAdapter.addHeader(cartHeaderView);

    final View cartFooterView =
        LayoutInflater.from(getContext()).inflate(R.layout.cart_footer, this, false);
    final Cart cart = mCartPref.get();
    if(cart != null) {
      final TextView totalView = ButterKnife.findById(cartFooterView, R.id.total_amount);
      totalView.setText(String.valueOf(cart.getTotal()));
    }
    mHeaderFooterAdapter.addFooter(cartFooterView);

    mHeaderFooterAdapter.getWrappedAdapter()
        .registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
          @Override public void onChanged() {
            if (mHeaderFooterAdapter.getWrappedAdapter().getItemCount() == 0) {
              mStateView.setText(R.string.cart_empty);
            }
            mAnimatorView.setDisplayedChildId(
                mHeaderFooterAdapter.getWrappedAdapter().getItemCount() == 0 ? R.id.state
                    : R.id.rv);
          }
        });
    mBooksView.setAdapter(mHeaderFooterAdapter);

    if (cart == null) {
      mStateView.setText(R.string.cart_empty);
      mAnimatorView.setDisplayedChildId(R.id.state);
    } else {
      mHeaderFooterAdapter.getWrappedAdapter().call(cart.getBooks());
      mHeaderFooterAdapter.notifyDataSetChanged();
    }

    //TODO request offer and apply it here
  }
}
