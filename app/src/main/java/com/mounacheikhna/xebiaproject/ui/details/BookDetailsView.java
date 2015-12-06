package com.mounacheikhna.xebiaproject.ui.details;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.PluralsRes;
import android.support.v4.app.ShareCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

  @Bind(R.id.ratings) Button mRatingsButton;
  @Bind(R.id.reviews) Button mReviewsButton;
  @Bind(R.id.share) Button mShareButton;
  private Activity mHost;
  private Book mBook;
  private BookResponse.GoodreadsBook mGoodreadsBook;

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

  public void bind(Activity host) {
    mHost = host;
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    HenriPotierApp.get(getContext()).getComponent().injectBookDetailsView(this);
  }

  public void display(Book book) {
    mBook = book;
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
    mGoodreadsBook = bookResponse.getFirstBook();
    if(mGoodreadsBook != null) {
      mDescriptionView.setText(mGoodreadsBook.getDescription());
      final BookResponse.Author author = mGoodreadsBook.getAuthor();
      if(author != null) {
        mAuthorView.setText(author.getName());
      }
    }

    final BookResponse.Work work = bookResponse.getFirsWork();
    if(work != null) {
      mRatingValueView.setText(work.getAverageRating());
      mRatingVotesView.setText(buildPluralsValue(work.getRatingsCount(), R.plurals.votes));

      mReviewsButton.setText(buildPluralsValue(work.getTextReviewsCount(), R.plurals.reviews));
      mRatingsButton.setText(buildPluralsValue(work.getRatingsCount(), R.plurals.ratings));
    }

  }

  public String buildPluralsValue(Integer values, @PluralsRes int pluralId) {
    if (values == null || values < 0) {
      values = 0;
    }
    return getResources().getQuantityString(pluralId, values, values);
  }

  @OnClick(R.id.share)
  public void OnShare() {
    ShareCompat.IntentBuilder.from(mHost)
        .setText(getTextToShare())
        .setType("text/plain")
        .setSubject(mBook.getTitle())
        .startChooser();
  }

  private String getTextToShare() {
    final StringBuilder builder =
        new StringBuilder().append("“").append(mBook.getTitle());
    if(mGoodreadsBook != null) {
      builder.append("” by ")
          .append(mGoodreadsBook.getAuthor().getName())
          .append("\n")
          .append(mGoodreadsBook.getImageUrl());
    }
    return builder.toString();
  }

}
