package com.mounacheikhna.xebiaproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.xebiaproject.ui.book.BooksGridView;

/**
 * Created by mouna on 02/12/15.
 */
public class MainActivity extends AppCompatActivity {

  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.books_grid_view) BooksGridView mBooksGridView;
  @Bind(R.id.cart_fab) FloatingActionButton mCartFab;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);

    mBooksGridView.bind(this);
  }

}
