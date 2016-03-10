package com.mounacheikhna.harrypotterbooks.ui.base;

import android.view.View;
import com.mounacheikhna.harrypotterbooks.api.harrypotter.model.Book;
import com.squareup.picasso.Picasso;

/**
 * Created by mouna on 05/12/15.
 */
public interface BindableBookItem {
  void bindTo(Book book, Picasso picasso);

  View getImageView();
}
