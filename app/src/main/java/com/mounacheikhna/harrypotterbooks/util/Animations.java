package com.mounacheikhna.harrypotterbooks.util;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;
import android.util.ArrayMap;
import android.util.Property;
import java.util.ArrayList;

/**
 * Created by cheikhnamouna on 12/3/15.
 */
public final class Animations {

  private Animations() {
  }

  /**
   * An implementation of {@link android.util.Property} to be used specifically with fields of
   * type
   * <code>float</code>. This type-specific subclass enables performance benefit by allowing
   * calls to a {@link #set(Object, Float) set()} function that takes the primitive
   * <code>float</code> type and avoids autoboxing and other overhead associated with the
   * <code>Float</code> class.
   *
   * @param <T> The class on which the Property is declared.
   **/
  public static abstract class FloatProperty<T> extends Property<T, Float> {
    public FloatProperty(String name) {
      super(Float.class, name);
    }

    /**
     * A type-specific override of the {@link #set(Object, Float)} that is faster when dealing
     * with fields of type <code>float</code>.
     */
    public abstract void setValue(T object, float value);

    @Override final public void set(T object, Float value) {
      setValue(object, value);
    }
  }

  /**
   * An implementation of {@link android.util.Property} to be used specifically with fields of
   * type
   * <code>int</code>. This type-specific subclass enables performance benefit by allowing
   * calls to a {@link #set(Object, Integer) set()} function that takes the primitive
   * <code>int</code> type and avoids autoboxing and other overhead associated with the
   * <code>Integer</code> class.
   *
   * @param <T> The class on which the Property is declared.
   */
  public static abstract class IntProperty<T> extends Property<T, Integer> {

    public IntProperty(String name) {
      super(Integer.class, name);
    }

    /**
     * A type-specific override of the {@link #set(Object, Integer)} that is faster when dealing
     * with fields of type <code>int</code>.
     */
    public abstract void setValue(T object, int value);

    @Override final public void set(T object, Integer value) {
      setValue(object, value.intValue());
    }
  }

  @TargetApi(Build.VERSION_CODES.KITKAT) public static class EmptyTransitionListener
      implements Transition.TransitionListener {

    @Override public void onTransitionStart(Transition transition) {

    }

    @Override public void onTransitionEnd(Transition transition) {

    }

    @Override public void onTransitionCancel(Transition transition) {

    }

    @Override public void onTransitionPause(Transition transition) {

    }

    @Override public void onTransitionResume(Transition transition) {

    }
  }

  static class AnimatorListenerWrapper implements Animator.AnimatorListener {
    private final Animator mAnimator;
    private final Animator.AnimatorListener mListener;

    public AnimatorListenerWrapper(Animator animator, Animator.AnimatorListener listener) {
      mAnimator = animator;
      mListener = listener;
    }

    @Override public void onAnimationStart(Animator animator) {
      mListener.onAnimationStart(mAnimator);
    }

    @Override public void onAnimationEnd(Animator animator) {
      mListener.onAnimationEnd(mAnimator);
    }

    @Override public void onAnimationCancel(Animator animator) {
      mListener.onAnimationCancel(mAnimator);
    }

    @Override public void onAnimationRepeat(Animator animator) {
      mListener.onAnimationRepeat(mAnimator);
    }
  }

  /**
   * https://halfthought.wordpress.com/2014/11/07/reveal-transition/
   * <p/>
   * Interrupting Activity transitions can yield an OperationNotSupportedException when the
   * transition tries to pause the animator. Yikes! We can fix this by wrapping the Animator:
   */
  public static class NoPauseAnimator extends Animator {
    private final Animator mAnimator;
    private final ArrayMap<AnimatorListener, AnimatorListener> mListeners =
        new ArrayMap<AnimatorListener, AnimatorListener>();

    public NoPauseAnimator(Animator animator) {
      mAnimator = animator;
    }

    @Override public void addListener(AnimatorListener listener) {
      AnimatorListener wrapper = new AnimatorListenerWrapper(this, listener);
      if (!mListeners.containsKey(listener)) {
        mListeners.put(listener, wrapper);
        mAnimator.addListener(wrapper);
      }
    }

    @Override public void cancel() {
      mAnimator.cancel();
    }

    @Override public void end() {
      mAnimator.end();
    }

    @Override public long getDuration() {
      return mAnimator.getDuration();
    }

    @Override public TimeInterpolator getInterpolator() {
      return mAnimator.getInterpolator();
    }

    @Override public void setInterpolator(TimeInterpolator timeInterpolator) {
      mAnimator.setInterpolator(timeInterpolator);
    }

    @Override public ArrayList<AnimatorListener> getListeners() {
      return new ArrayList<AnimatorListener>(mListeners.keySet());
    }

    @Override public long getStartDelay() {
      return mAnimator.getStartDelay();
    }

    @Override public void setStartDelay(long delayMS) {
      mAnimator.setStartDelay(delayMS);
    }

    @Override public boolean isPaused() {
      return mAnimator.isPaused();
    }

    @Override public boolean isRunning() {
      return mAnimator.isRunning();
    }

    @Override public boolean isStarted() {
      return mAnimator.isStarted();
    }


    @Override public void removeAllListeners() {
      mListeners.clear();
      mAnimator.removeAllListeners();
    }

    @Override public void removeListener(AnimatorListener listener) {
      AnimatorListener wrapper = mListeners.get(listener);
      if (wrapper != null) {
        mListeners.remove(listener);
        mAnimator.removeListener(wrapper);
      }
    }

    @Override public Animator setDuration(long durationMS) {
      mAnimator.setDuration(durationMS);
      return this;
    }

    @Override public void setTarget(Object target) {
      mAnimator.setTarget(target);
    }

    @Override public void setupEndValues() {
      mAnimator.setupEndValues();
    }

    @Override public void setupStartValues() {
      mAnimator.setupStartValues();
    }

    @Override public void start() {
      mAnimator.start();
    }
  }
}
