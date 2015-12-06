package com.mounacheikhna.xebiaproject.ui.details;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.mounacheikhna.xebiaproject.api.goodreads.model.GoodreadsReview;

/**
 * Created by mouna on 06/12/15.
 */
public class ReviewItemView extends LinearLayout {

  public ReviewItemView(Context context) {
    super(context);
  }

  public ReviewItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void bindTo(GoodreadsReview review) {

  }
}
