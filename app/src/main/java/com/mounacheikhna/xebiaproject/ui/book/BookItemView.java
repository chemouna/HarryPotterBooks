package com.mounacheikhna.xebiaproject.ui.book;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.mounacheikhna.xebiaproject.api.model.Book;
import com.squareup.picasso.Picasso;

/**
 * Created by mouna on 02/12/15.
 */
public class BookItemView extends RelativeLayout {//maybe extend CardView instead ?
  public BookItemView(Context context) {
    super(context);
  }

  public BookItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public BookItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void bindTo(Book book, Picasso picasso) {

  }
}
