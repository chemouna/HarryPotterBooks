package com.mounacheikhna.xebiaproject.ui.book;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.xebiaproject.R;
import com.mounacheikhna.xebiaproject.ui.view.recyclerview.OffsetDecoration;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

/**
 * Created by mouna on 02/12/15.
 */
public class BooksGridView extends LinearLayout implements BooksScreen {

  @Bind(R.id.books_rv) RecyclerView mBooksView;
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

  private void init(Context context) {
    final View view = LayoutInflater.from(context).inflate(R.layout.books_view, this, true);
    setOrientation(VERTICAL);
    ButterKnife.bind(this, view);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    mBooksPresenter.onAttach(this);
    final int spacing = getResources().getDimensionPixelSize(R.dimen.spacing_very_small);
    mBooksView.addItemDecoration(new OffsetDecoration(spacing));
    mAdapter = new BooksAdapter(mPicasso);
    mBooksView.setAdapter(mAdapter);

    loadBooks();
  }

  private void loadBooks() {

  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mBooksPresenter.onDetach();
  }
}
