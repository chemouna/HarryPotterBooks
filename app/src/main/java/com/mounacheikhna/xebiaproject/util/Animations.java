package com.mounacheikhna.xebiaproject.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;
import android.util.Property;

/**
 * Created by cheikhnamouna on 12/3/15.
 */
public final class Animations {

  private Animations() {}

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

    @Override
    final public void set(T object, Float value) {
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

    @Override
    final public void set(T object, Integer value) {
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

}
