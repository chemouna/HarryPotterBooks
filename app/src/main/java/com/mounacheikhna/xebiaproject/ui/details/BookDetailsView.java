package com.mounacheikhna.xebiaproject.ui.details;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.PluralsRes;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
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
import com.mounacheikhna.xebiaproject.api.goodreads.model.GoodreadsBook;
import com.mounacheikhna.xebiaproject.api.goodreads.model.GoodreadsBook.Author;
import com.mounacheikhna.xebiaproject.api.goodreads.model.GoodreadsResponse;
import com.mounacheikhna.xebiaproject.api.goodreads.model.Work;
import com.mounacheikhna.xebiaproject.api.henripotier.model.Book;
import com.mounacheikhna.xebiaproject.ui.view.DetailItemLayout;
import com.mounacheikhna.xebiaproject.ui.view.ExpandingTextView;
import javax.inject.Inject;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
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
  @Bind(R.id.publication_date_layout) DetailItemLayout mReleaseLayout;
  @Bind(R.id.reviews_rv) RecyclerView mReviewsRv;

  private Activity mHost;
  private Book mBook;
  private GoodreadsBook mGoodreadsBook;
  private ReviewAdapter mAdapter;
  private CompositeSubscription mSubscriptions;

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
    mSubscriptions = new CompositeSubscription();
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
    mSubscriptions.add(mBookDetailsPresenter.searchGoodreadsBook(book.getTitle())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<GoodreadsResponse>() {
          @Override public void onCompleted() {
          }

          @Override public void onError(Throwable e) {
            Timber.e(e, " error ");
          }

          @Override public void onNext(GoodreadsResponse goodreadsResponse) {
            Timber.d("goodreadsResponse : %s", goodreadsResponse);
            displayGoodreadsDetails(goodreadsResponse);
          }
        }));
  }

  private void displayGoodreadsDetails(GoodreadsResponse goodreadsResponse) {
    if (goodreadsResponse == null) return;
    mGoodreadsBook = goodreadsResponse.getFirstBook();
    if (mGoodreadsBook != null) {
      mDescriptionView.setText(mGoodreadsBook.getDescription());
      final Author author = mGoodreadsBook.getAuthor();
      /*if(author != null) {
        mAuthorView.setText(author.getName());
      }*/
      displayReviews(mGoodreadsBook.getId());
    }

    final Work work = goodreadsResponse.getFirsWork();
    if (work != null) {
      mRatingValueView.setText(work.getAverageRating());
      mRatingVotesView.setText(buildPluralsValue(work.getRatingsCount(), R.plurals.votes));
      mReviewsButton.setText(buildPluralsValue(work.getTextReviewsCount(), R.plurals.reviews));
      mRatingsButton.setText(buildPluralsValue(work.getRatingsCount(), R.plurals.ratings));
      mReleaseLayout.setContentText(work.getFormattedReleaseDate());
    }
  }

  private void displayReviews(String bookId) {
    mAdapter = new ReviewAdapter();
    mSubscriptions.add(mBookDetailsPresenter.fetchReviews(bookId)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<GoodreadsResponse>() {
          @Override public void onCompleted() {
          }

          @Override public void onError(Throwable e) {
            Timber.e(e, " error ");
          }

          @Override public void onNext(GoodreadsResponse goodreadsResponse) {
            Timber.d("goodreadsResponse : %s", goodreadsResponse);
          }
        }));
  }

  public String buildPluralsValue(Integer values, @PluralsRes int pluralId) {
    if (values == null || values < 0) {
      values = 0;
    }
    return getResources().getQuantityString(pluralId, values, values);
  }

  @OnClick(R.id.share) public void OnShare() {
    ShareCompat.IntentBuilder.from(mHost)
        .setText(getTextToShare())
        .setType("text/plain")
        .setSubject(mBook.getTitle())
        .startChooser();
  }

  private String getTextToShare() {
    final StringBuilder builder = new StringBuilder().append("“").append(mBook.getTitle());
    if (mGoodreadsBook != null) {
      builder.append("” by ")
          .append(mGoodreadsBook.getAuthor().getName())
          .append("\n")
          .append(mGoodreadsBook.getImageUrl());
    }
    return builder.toString();
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mBookDetailsPresenter.onDetach();
    mSubscriptions.clear();
  }

}
