package com.mounacheikhna.xebiaproject.api.henripotier.model;

/**
 * Created by mouna on 02/12/15.
 */
public class Offer {

  public static final String TYPE_PERCENTAGE = "percentage";
  public static final String TYPE_MINUS = "minus";
  public static final String TYPE_SLICE = "slice";

  private String type;
  private int sliceValue;
  private int value;

  public String getType() {
    return type;
  }

  public int getSliceValue() {
    return sliceValue;
  }

  public int getValue() {
    return value;
  }
}
