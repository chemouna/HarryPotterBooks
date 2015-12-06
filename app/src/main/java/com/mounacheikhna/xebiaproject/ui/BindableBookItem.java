package com.mounacheikhna.xebiaproject.ui;

import android.view.View;
import com.mounacheikhna.xebiaproject.api.henripotier.model.Book;
import com.squareup.picasso.Picasso;

/**
 * Created by mouna on 05/12/15.
 */
public interface BindableBookItem {
  void bindTo(Book book, Picasso picasso);

  View getImageView();
}
