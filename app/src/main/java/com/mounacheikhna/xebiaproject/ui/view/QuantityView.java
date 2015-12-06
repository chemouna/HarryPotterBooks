package com.mounacheikhna.xebiaproject.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mounacheikhna.xebiaproject.R;

/**
 * Created by cheikhna on 03/12/15.
 */
public class QuantityView extends LinearLayout {

  @Bind(R.id.remove_quantity) ImageView mRemoveView;
  @Bind(R.id.add_quantity) ImageView mAddView;
  @Bind(R.id.tv_quantity) TextView mQuantityTextView;

  private int mQuantity;

  @Nullable private OnQuantityChangeListener mOnQuantityChangeListener;
  private String mKey;

  public QuantityView(Context context) {
    this(context, null);
  }

  public QuantityView(final Context context, final AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public QuantityView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    if (isInEditMode()) return;
    inflate(getContext(), R.layout.quantity_view, this);
    ButterKnife.bind(this);
    setOrientation(HORIZONTAL);
    setQuantity(1);
  }

  public void setKey(@NonNull String key) {
    mKey = key;
  }

  public void setQuantity(int quantity) {
    mQuantity = quantity;
    updateQuantity();
  }

  @OnClick(R.id.remove_quantity) public void onRemoveQuantity() {
    mQuantity--;
    updateQuantity();
  }

  @OnClick(R.id.add_quantity) public void onAddQuantity() {
    mQuantity++;
    updateQuantity();
  }

  public void setOnQuantityChangeListener(@Nullable OnQuantityChangeListener listener) {
    mOnQuantityChangeListener = listener;
  }

  private void updateQuantity() {
    mQuantityTextView.setText(String.valueOf(mQuantity));
    if (mOnQuantityChangeListener != null) {
      mOnQuantityChangeListener.onQuantityChange(mKey, mQuantity);
    }
  }

  public int getQuantity() {
    return mQuantity;
  }

  public interface OnQuantityChangeListener {
    void onQuantityChange(String key, int quantity);
  }
}
