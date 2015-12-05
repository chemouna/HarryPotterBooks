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
import com.mounacheikhna.xebiaproject.api.model.OfferResponse;
import com.mounacheikhna.xebiaproject.data.Cart;
import com.mounacheikhna.xebiaproject.ui.book.BooksAdapter;
import com.mounacheikhna.xebiaproject.ui.view.CustomViewAnimator;
import com.mounacheikhna.xebiaproject.ui.view.recyclerview.HeaderFooterAdapter;
import com.mounacheikhna.xebiaproject.util.PriceFormatter;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by mouna on 05/12/15.
 */
public class CartView extends FrameLayout implements CartScreen {

  @Bind(R.id.rv) RecyclerView mBooksView;
  @Bind(R.id.container_animator) CustomViewAnimator mAnimatorView;
  @Bind(R.id.state) TextView mStateView;

  @Inject @Named("cart") Preference<Cart> mCartPref;
  @Inject Picasso mPicasso;
  @Inject CartPresenter mCartPresenter;

  private HeaderFooterAdapter<BooksAdapter> mHeaderFooterAdapter;
  private TextView mTotalView;
  private TextView mOfferView;

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
    addTitleHeader();
    addPromoFooter();
    addTotalFooter();

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

    final Cart cart = mCartPref.get();
    if (cart == null) {
      mStateView.setText(R.string.cart_empty);
      mAnimatorView.setDisplayedChildId(R.id.state);
    } else {
      mHeaderFooterAdapter.getWrappedAdapter().call(cart.getBooks());
      mHeaderFooterAdapter.notifyDataSetChanged();
    }

    //TODO: apply after commercial offer
    if(cart != null) {
      mCartPresenter.getOffers(cart.getBooks()).subscribe(new Subscriber<OfferResponse>() {
        @Override public void onCompleted() {}

        @Override public void onError(Throwable e) {
          Timber.e("Error : "+ e);
        }

        @Override public void onNext(OfferResponse offerResponse) {
          final Cart.OfferPrice bestOffer = cart.getBestOffer(offerResponse.getOffers());
          Timber.d(" bestOffer : "+ bestOffer);
          mOfferView.setText(PriceFormatter.formatEuro(bestOffer.getPromo()));
          mTotalView.setText(PriceFormatter.formatEuro(bestOffer.getPrice()));
        }
      });
    }
  }

  private void addTitleHeader() {
    final View cartHeaderView =
        LayoutInflater.from(getContext()).inflate(R.layout.cart_header, this, false);
    mHeaderFooterAdapter.addHeader(cartHeaderView);
  }

  private void addTotalFooter() {
    Cart cart = mCartPref.get();
    final View cartFooterView =
        LayoutInflater.from(getContext()).inflate(R.layout.cart_footer, this, false);
    if(cart != null) {
      mTotalView = ButterKnife.findById(cartFooterView, R.id.total_amount);
      mTotalView.setText(PriceFormatter.formatEuro(cart.getTotal()));
    }
    mHeaderFooterAdapter.addFooter(cartFooterView);
  }

  private void addPromoFooter() {
    Cart cart = mCartPref.get();
    final View promoFooterView =
        LayoutInflater.from(getContext()).inflate(R.layout.cart_offer_footer, this, false);
    if(cart != null) {
      mOfferView = ButterKnife.findById(promoFooterView, R.id.offer_amount);
      //mOfferView.setText(PriceFormatter.formatEuro(cart.get()));
    }
    mHeaderFooterAdapter.addFooter(promoFooterView);
  }
}
