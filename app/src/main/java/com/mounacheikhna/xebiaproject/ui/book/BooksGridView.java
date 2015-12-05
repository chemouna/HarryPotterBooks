package com.mounacheikhna.xebiaproject.ui.book;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.xebiaproject.HenriPotierApp;
import com.mounacheikhna.xebiaproject.R;
import com.mounacheikhna.xebiaproject.api.model.Book;
import com.mounacheikhna.xebiaproject.ui.view.CustomViewAnimator;
import com.mounacheikhna.xebiaproject.ui.view.recyclerview.OffsetDecoration;
import com.squareup.picasso.Picasso;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.mounacheikhna.xebiaproject.util.ApiLevels.isAtLeastLollipop;

/**
 * Created by mouna on 02/12/15.
 */
public class BooksGridView extends LinearLayout implements BooksScreen {

  @Bind(R.id.books_rv) RecyclerView mBooksView;
  @Bind(R.id.container_animator) CustomViewAnimator mAnimatorView;
  @Bind(R.id.state) TextView mStateView;
  @Inject Picasso mPicasso;
  @Inject BooksPresenter mBooksPresenter;
  private BooksAdapter mAdapter;
  @Nullable private Activity mHost;

  public BooksGridView(Context context) {
    super(context);
    init(context);
  }

  public BooksGridView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public BooksGridView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public BooksGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  private void init(Context context) {
    if (isInEditMode()) return;
    final View view = LayoutInflater.from(context).inflate(R.layout.books_view, this, true);
    setOrientation(VERTICAL);
    setClipToPadding(false);
    ButterKnife.bind(this, view);
  }

  public void bind(Activity host) {
    mHost = host;
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    //temp for now -> TODO: later set an injection per component (i.e BooksComponent and scoped to it -> @see mrgabriel)
    HenriPotierApp.get(getContext()).getComponent().injectBooksView(this);

    mBooksPresenter.onAttach(this);
    initList();
    loadBooks();
  }

  private void loadBooks() {
    if (!isConnected()) {
      mStateView.setText(R.string.no_network);
      return;
    }
    mBooksPresenter.loadBooks()
        //.delay(25, TimeUnit.SECONDS) //just for debugging
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List<Book>>() {
          @Override public void onCompleted() {
          }

          @Override public void onError(Throwable e) {
            mStateView.setText(R.string.loading_error);
            mAnimatorView.setDisplayedChildId(R.id.state);
          }

          @Override public void onNext(List<Book> books) {
            mAdapter.call(books);
          }
        });
  }

  private boolean isConnected() {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }

  private void initList() {
    mAdapter = new BooksAdapter(mPicasso);
    mAnimatorView.setDisplayedChildId(R.id.progress);
    mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override public void onChanged() {
        if (mAdapter.getItemCount() == 0) {
          mStateView.setText(R.string.no_books);
        }
        mAnimatorView.setDisplayedChildId(
            mAdapter.getItemCount() == 0 ? R.id.state : R.id.books_rv);
      }
    });
    mAdapter.setBookSelectedListener(new BooksAdapter.BookSelectedListener() {
      @SuppressLint("NewApi")
      @Override public void onBookSelectedListener(View transitionView, Book book) {
        if (mHost == null) return;
        final Intent intent = BookActivity.getIntent(getContext(), book);
        if (isAtLeastLollipop()) {
          ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mHost,
              android.util.Pair.create(transitionView,
                  mHost.getString(R.string.transition_book_image)),
              android.util.Pair.create(transitionView,
                  mHost.getString(R.string.transition_book_container)));
          ActivityCompat.startActivity(mHost, intent, options.toBundle());
        } else {
          mHost.startActivity(intent);
        }
      }
    });
    final int spacing = getResources().getDimensionPixelSize(R.dimen.spacing_small);
    mBooksView.addItemDecoration(new OffsetDecoration(spacing));
    mBooksView.setAdapter(mAdapter);
    mBooksView.scheduleLayoutAnimation();
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mBooksPresenter.onDetach();
  }
}
