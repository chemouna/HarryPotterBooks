package com.mounacheikhna.xebiaproject.util;

import android.annotation.SuppressLint;

/**
 * Created by cheikhnamouna on 12/3/15.
 */
public class PriceFormatter {

  @SuppressLint("DefaultLocale") public static String formatPrice(float price) {
    return String.format("%.2f", price);
  }

  public static String formatEuro(float price) {
    return formatPrice(price) + "€";
  }
}
