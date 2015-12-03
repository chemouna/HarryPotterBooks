package com.mounacheikhna.xebiaproject.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;

/**
 * Created by cheikhnamouna on 12/3/15.
 */
public final class Animations {


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
