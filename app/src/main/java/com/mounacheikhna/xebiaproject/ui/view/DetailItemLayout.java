package com.mounacheikhna.xebiaproject.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mounacheikhna.xebiaproject.R;

/**
 * Created by mouna on 06/12/15.
 */
public class DetailItemLayout extends LinearLayout {

  private final TextView mTitleTextView;
  private final TextView mContentTextView;

  public DetailItemLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    LayoutInflater.from(context).inflate(R.layout.detail_item_layout, this, true);
    mTitleTextView = (TextView) findViewById(android.R.id.text1);
    mContentTextView = (TextView) findViewById(android.R.id.text2);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DetailItemLayout);
    final String title = a.getString(R.styleable.DetailItemLayout_title);
    if (!TextUtils.isEmpty(title)) {
      mTitleTextView.setText(title);
    }
    a.recycle();
  }

  public TextView getTitleTextView() {
    return mTitleTextView;
  }

  public TextView getContentTextView() {
    return mContentTextView;
  }

  public void setContentText(CharSequence text) {
    mContentTextView.setText(text);
  }
}
