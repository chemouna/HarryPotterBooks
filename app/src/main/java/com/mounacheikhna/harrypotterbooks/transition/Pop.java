/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mounacheikhna.harrypotterbooks.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.mounacheikhna.harrypotterbooks.util.Animations;

/**
 * A transition that animates the alpha & scale X & Y of a view simultaneously.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP) public class Pop extends Transition {

  private static final String PROPNAME_ALPHA = "com:mounacheikhna:xebiaproject:pop:alpha";
  private static final String PROPNAME_SCALE_X = "com:mounacheikhna:xebiaproject:pop:scaleX";
  private static final String PROPNAME_SCALE_Y = "com:mounacheikhna:xebiaproject:pop:scaleY";

  private static final String[] transitionProperties = {
      PROPNAME_ALPHA, PROPNAME_SCALE_X, PROPNAME_SCALE_Y
  };

  public Pop(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public String[] getTransitionProperties() {
    return transitionProperties;
  }

  @Override public void captureStartValues(TransitionValues transitionValues) {
    transitionValues.values.put(PROPNAME_ALPHA, 0f);
    transitionValues.values.put(PROPNAME_SCALE_X, 0f);
    transitionValues.values.put(PROPNAME_SCALE_Y, 0f);
  }

  @Override public void captureEndValues(TransitionValues transitionValues) {
    transitionValues.values.put(PROPNAME_ALPHA, 1f);
    transitionValues.values.put(PROPNAME_SCALE_X, 1f);
    transitionValues.values.put(PROPNAME_SCALE_Y, 1f);
  }

  @Override public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
      TransitionValues endValues) {
    PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f);
    PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, 1f);
    PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f, 1f);
    return new Animations.NoPauseAnimator(
        ObjectAnimator.ofPropertyValuesHolder(endValues.view, alpha, scaleX, scaleY));
  }
}
