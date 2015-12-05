package com.mounacheikhna.xebiaproject.ui.book;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import com.mounacheikhna.xebiaproject.R;

/**
 * Created by cheikhnamouna on 12/3/15.
 */
public class BookDetailsView extends LinearLayout {

  private Activity mHost;

  public BookDetailsView(Context context) {
    super(context);
    init(context);
  }

  public BookDetailsView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public void bind(Activity host) {
    mHost = host;
  }

  private void init(Context context) {
    if (isInEditMode()) return;
    LayoutInflater.from(context).inflate(R.layout.book_details_view, this, true);
    setOrientation(VERTICAL);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }
}
