package com.mounacheikhna.xebiaproject.ui.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.mounacheikhna.xebiaproject.R;
import com.mounacheikhna.xebiaproject.api.goodreads.model.GoodreadsReview;
import java.util.ArrayList;
import java.util.List;
import rx.functions.Action1;

/**
 * Created by mouna on 06/12/15.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>
    implements Action1<List<GoodreadsReview>> {

  private List<GoodreadsReview> mItems = new ArrayList<>();

  @Override public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ReviewViewHolder((ReviewItemView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.review_item, parent, false));
  }

  @Override public void onBindViewHolder(ReviewViewHolder holder, int position) {
    final GoodreadsReview review = mItems.get(position);
    holder.bindTo(review);
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  @Override public void call(List<GoodreadsReview> goodreadsReviews) {
    mItems = goodreadsReviews;
    notifyDataSetChanged();
  }

  public static class ReviewViewHolder extends RecyclerView.ViewHolder {
    public final ReviewItemView itemView;

    public ReviewViewHolder(ReviewItemView itemView) {
      super(itemView);
      this.itemView = itemView;
    }

    public void bindTo(GoodreadsReview review) {
      itemView.bindTo(review);
    }
  }
}
