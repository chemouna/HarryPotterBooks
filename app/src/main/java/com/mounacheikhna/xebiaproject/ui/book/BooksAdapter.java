package com.mounacheikhna.xebiaproject.ui.book;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.mounacheikhna.xebiaproject.R;
import com.mounacheikhna.xebiaproject.api.model.Book;
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

  public BooksAdapter(Picasso picasso) {
    mPicasso = picasso;
  }

  @Override public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new BookViewHolder((BookItemView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.book_item, parent, false));
  }

  @Override public void onBindViewHolder(BookViewHolder holder, int position) {
    holder.bindTo(mItems.get(position));
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  @Override public void call(List<Book> books) {
    mItems = books;
    notifyDataSetChanged();
  }

  public final class BookViewHolder extends RecyclerView.ViewHolder {
    public final BookItemView itemView;

    public BookViewHolder(BookItemView itemView) {
      super(itemView);
      this.itemView = itemView;
    }

    public void bindTo(Book book) {
      itemView.bindTo(book, mPicasso);
    }
  }
}
