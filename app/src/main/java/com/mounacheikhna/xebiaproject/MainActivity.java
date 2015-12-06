package com.mounacheikhna.xebiaproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Property;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mounacheikhna.xebiaproject.ui.book.BooksGridView;
import com.mounacheikhna.xebiaproject.ui.cart.CartView;
import com.mounacheikhna.xebiaproject.util.Animations;

import static com.mounacheikhna.xebiaproject.util.ApiLevels.isAtLeastLollipop;

/**
 * Created by mouna on 02/12/15.
 */
public class MainActivity extends AppCompatActivity {

  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.books_grid_view) BooksGridView mBooksGridView;
  @Bind(R.id.cart_fab) FloatingActionButton mCartFab;
  @Bind(R.id.cart_view) CartView mCartView;

  private Animator mCircularReveal;
  private Interpolator mInterpolator;
  private ObjectAnimator mColorChange;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    mBooksGridView.bind(this);
    mInterpolator = new FastOutSlowInInterpolator();
  }

  @OnClick(R.id.cart_fab) public void onFabClick() {
    if (isAtLeastLollipop()) {
      revealCartLollipop(mCartFab, mCartView);
    } else {
      showCart();
    }
  }

  private void revealCartLollipop(final View clickedView, final CartView container) {
    prepareCircularReveal(clickedView, container);
    ViewCompat.animate(clickedView)
        .scaleX(0)
        .scaleY(0)
        .alpha(0)
        .setInterpolator(mInterpolator)
        .setListener(new ViewPropertyAnimatorListenerAdapter() {
          @Override public void onAnimationEnd(View view) {
            showCart();
          }
        })
        .start();

    container.setVisibility(View.VISIBLE);
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.play(mCircularReveal).with(mColorChange);
    animatorSet.start();
  }

  private void showCart() {
    mCartView.setVisibility(View.VISIBLE);
    mCartFab.setVisibility(View.GONE);
    mBooksGridView.setVisibility(View.GONE);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void prepareCircularReveal(View startView, FrameLayout targetView) {
    int centerX = (startView.getLeft() + startView.getRight()) / 2;
    // Subtract the start view's height to adjust for relative coordinates on screen.
    int centerY = (startView.getTop() + startView.getBottom()) / 2 - startView.getHeight();
    float endRadius = (float) Math.hypot((double) centerX, (double) centerY);
    mCircularReveal =
        ViewAnimationUtils.createCircularReveal(targetView, centerX, centerY, startView.getWidth(),
            endRadius);
    mCircularReveal.setInterpolator(new FastOutLinearInInterpolator());

    mCircularReveal.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        mCircularReveal.removeListener(this);
      }
    });
    // Adding a color animation from the FAB's color to transparent creates a dissolve like
    // effect to the circular reveal.
    int accentColor = ContextCompat.getColor(this, R.color.accent);
    mColorChange =
        ObjectAnimator.ofInt(targetView, FOREGROUND_COLOR, accentColor, Color.TRANSPARENT);
    mColorChange.setEvaluator(new ArgbEvaluator());
    mColorChange.setInterpolator(mInterpolator);
  }

  public static final Property<FrameLayout, Integer> FOREGROUND_COLOR =
      new Animations.IntProperty<FrameLayout>("foregroundColor") {

        @Override public void setValue(FrameLayout layout, int value) {
          if (layout.getForeground() instanceof ColorDrawable) {
            ((ColorDrawable) layout.getForeground().mutate()).setColor(value);
          } else {
            layout.setForeground(new ColorDrawable(value));
          }
        }

        @Override public Integer get(FrameLayout layout) {
          if (layout.getForeground() instanceof ColorDrawable) {
            return ((ColorDrawable) layout.getForeground()).getColor();
          } else {
            return Color.TRANSPARENT;
          }
        }
      };

  /*@Override public void onBackPressed() {
    if(mCartView.getVisibility() == View.VISIBLE) {
      mCartView.setVisibility(View.GONE);
      mCartFab.setVisibility(View.VISIBLE);
      mBooksGridView.setVisibility(View.VISIBLE);
    }
    else {
      super.onBackPressed();
    }
  }*/
}
