package com.mounacheikhna.xebiaproject.ui.book;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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
import com.mounacheikhna.xebiaproject.ui.view.CustomViewAnimator;
import com.mounacheikhna.xebiaproject.ui.view.recyclerview.OffsetDecoration;
import com.squareup.picasso.Picasso;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mouna on 02/12/15.
 */
public class BooksGridView extends LinearLayout implements BooksScreen {

  @Bind(R.id.books_rv) RecyclerView mBooksView;
  @Bind(R.id.container_animator) CustomViewAnimator mAnimatorView;
  @Bind(R.id.state) TextView mStateView;

  private BooksAdapter mAdapter;

  @Inject Picasso mPicasso;
  @Inject BooksPresenter mBooksPresenter;

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
    if(isInEditMode()) return;
    final View view = LayoutInflater.from(context).inflate(R.layout.books_view, this, true);
    setOrientation(VERTICAL);
    ButterKnife.bind(this, view);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    //temp for now -> TODO: later set an injection per component (i.e BooksComponent and scoped to it -> @see mrgabriel)
    HenriPotierApp.get(getContext()).getComponent().injectBooksView(this);

    mBooksPresenter.onAttach(this);
    initList();
    mBooksPresenter.loadBooks()
        .delay(16, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(mAdapter);
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

    final int spacing = getResources().getDimensionPixelSize(R.dimen.spacing_very_small);
    mBooksView.addItemDecoration(new OffsetDecoration(spacing));
    mBooksView.setAdapter(mAdapter);
    mBooksView.scheduleLayoutAnimation();
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mBooksPresenter.onDetach();
  }
}
