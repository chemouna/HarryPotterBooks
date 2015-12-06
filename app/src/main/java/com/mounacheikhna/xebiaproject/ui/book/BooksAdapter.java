package com.mounacheikhna.xebiaproject.ui.book;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mounacheikhna.xebiaproject.api.henripotier.model.Book;
import com.mounacheikhna.xebiaproject.ui.BindableBookItem;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import rx.functions.Action1;

/**
 * Created by mouna on 02/12/15.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder>
    implements Action1<List<Book>> {

  private final Picasso mPicasso;
  private List<Book> mItems = new ArrayList<>();
  @Nullable private BookSelectedListener mBookSelectedListener;
  private int mItemLayoutId;

  public BooksAdapter(int layoutId, Picasso picasso) {
    mPicasso = picasso;
    mItemLayoutId = layoutId;
  }

  @Override public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new BookViewHolder((BindableBookItem) LayoutInflater.from(parent.getContext())
        .inflate(mItemLayoutId, parent, false));
  }

  @Override public void onBindViewHolder(final BookViewHolder holder, int position) {
    final Book book = mItems.get(position);
    holder.bindTo(book);

    ((ViewGroup) holder.itemView).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (mBookSelectedListener != null) {
          mBookSelectedListener.onBookSelectedListener(holder.itemView.getImageView(), book);
        }
      }
    });
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  @Override public void call(List<Book> books) {
    mItems = books;
    notifyDataSetChanged();
  }

  public void setBookSelectedListener(@Nullable BookSelectedListener bookSelectedListener) {
    mBookSelectedListener = bookSelectedListener;
  }

  public final class BookViewHolder extends RecyclerView.ViewHolder {
    public final BindableBookItem itemView;

    public BookViewHolder(BindableBookItem itemView) {
      super((ViewGroup) itemView);
      this.itemView = itemView;
    }

    public void bindTo(Book book) {
      itemView.bindTo(book, mPicasso);
    }
  }

  public interface BookSelectedListener {
    void onBookSelectedListener(@Nullable View transitionView, Book book);
  }
}
