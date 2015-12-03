package com.mounacheikhna.xebiaproject.ui.book;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.xebiaproject.R;
import com.mounacheikhna.xebiaproject.api.model.Book;
import com.squareup.picasso.Picasso;

/**
 * Created by mouna on 02/12/15.
 */
public class BookItemView extends RelativeLayout {//maybe extend CardView instead ?

  @Bind(R.id.book_image) ImageView mBookImage;

  public BookItemView(Context context) {
    super(context);
  }

  public void bindTo(Book book, Picasso picasso) {
    if (!TextUtils.isEmpty(book.getCover())) {
      picasso.load(book.getCover())
          //.placeholder(R.drawable.ic_city)
          //.error(R.drawable.ic_city)
          .fit()
          .into(mBookImage);
    }
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }
}
