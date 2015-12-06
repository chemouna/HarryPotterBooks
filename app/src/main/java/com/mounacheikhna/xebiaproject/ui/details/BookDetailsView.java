package com.mounacheikhna.xebiaproject.ui.details;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.xebiaproject.HenriPotierApp;
import com.mounacheikhna.xebiaproject.R;
import com.mounacheikhna.xebiaproject.api.model.Book;
import com.mounacheikhna.xebiaproject.api.model.BookResponse;
import com.mounacheikhna.xebiaproject.ui.view.ExpandingTextView;
import javax.inject.Inject;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by cheikhnamouna on 12/3/15.
 */
public class BookDetailsView extends LinearLayout implements BookDetailsScreen {

  @Inject BookDetailsPresenter mBookDetailsPresenter;

  @Bind(R.id.description) ExpandingTextView mDescriptionView;
  @Bind(R.id.author) TextView mAuthorView;
  @Bind(R.id.tv_rating_value) TextView mRatingValueView;
  @Bind(R.id.tv_rating_votes) TextView mRatingVotesView;

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
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<BookResponse>() {
          @Override public void onCompleted() {
          }

          @Override public void onError(Throwable e) {
            Timber.e(e, " error ");
          }

          @Override public void onNext(BookResponse bookResponse) {
            Timber.d("bookResponse : %s", bookResponse);
            displayGoodreadsDetails(bookResponse);
          }
        });
  }

  private void displayGoodreadsDetails(BookResponse bookResponse) {
    if(bookResponse == null) return;
    final BookResponse.GoodreadsBook goodreadsBook = bookResponse.getFirstBook();
    if(goodreadsBook != null) {
      mDescriptionView.setText(goodreadsBook.getDescription());
      final BookResponse.Author author = goodreadsBook.getAuthor();
      if(author != null) {
        mAuthorView.setText(author.getName());
      }
    }

    final BookResponse.Work work = bookResponse.getFirsWork();
    if(work != null) {
      mRatingValueView.setText(work.getAverageRating());
      mRatingVotesView.setText(buildRatingVotesValue(work.getRatingsCount()));
    }

  }

  public String buildRatingVotesValue(Integer votes) {
    if (votes == null || votes < 0) {
      votes = 0;
    }
    return getResources().getQuantityString(R.plurals.votes, votes, votes);
  }

}
