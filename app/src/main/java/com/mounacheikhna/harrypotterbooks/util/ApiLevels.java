package com.mounacheikhna.harrypotterbooks.util;

import android.os.Build;

/**
 * Created by mouna on 03/12/15.
 */
public final class ApiLevels {

  private ApiLevels() {
  }

  public static boolean isAtLeastM() {
    return isAtLeast(Build.VERSION_CODES.M);
  }

  public static boolean isAtLeastLollipop() {
    return isAtLeast(Build.VERSION_CODES.LOLLIPOP);
  }

  public static boolean isAtLeast(int apiLevel) {
    return Build.VERSION.SDK_INT >= apiLevel;
  }
}
