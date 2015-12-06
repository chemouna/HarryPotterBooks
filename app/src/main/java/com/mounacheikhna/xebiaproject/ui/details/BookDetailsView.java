package com.mounacheikhna.xebiaproject.ui.details;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import com.mounacheikhna.xebiaproject.HenriPotierApp;
import com.mounacheikhna.xebiaproject.R;
import com.mounacheikhna.xebiaproject.api.model.Book;
import com.mounacheikhna.xebiaproject.api.model.BookResponse;
import javax.inject.Inject;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by cheikhnamouna on 12/3/15.
 */
public class BookDetailsView extends LinearLayout implements BookDetailsScreen {

  @Inject BookDetailsPresenter mBookDetailsPresenter;

  public BookDetailsView(Context context) {
    super(context);
    init(context);
  }

  public BookDetailsView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  private void init(Context context) {
    if (isInEditMode()) return;
    LayoutInflater.from(context).inflate(R.layout.book_details_view, this, true);
    setOrientation(VERTICAL);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    HenriPotierApp.get(getContext()).getComponent().injectBookDetailsView(this);
  }

  public void display(Book book) {
    mBookDetailsPresenter.searchGoodreadsBook(book.getTitle())
        .subscribe(new Subscriber<BookResponse>() {
          @Override public void onCompleted() {
          }

          @Override public void onError(Throwable e) {
            Timber.e(e, " error ");
          }

          @Override public void onNext(BookResponse bookResponse) {
            Timber.d("bookResponse : %s", bookResponse);
          }
        });
  }
}
