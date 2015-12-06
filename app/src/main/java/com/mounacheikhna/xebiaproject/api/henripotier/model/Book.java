package com.mounacheikhna.xebiaproject.api.henripotier.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mouna on 02/12/15.
 */
public class Book implements Parcelable {

  private String isbn;
  private String title;
  private int price;
  private String cover;
  private int quantity;

  public String getIsbn() {
    return isbn;
  }

  public String getTitle() {
    return title;
  }

  public int getPrice() {
    return price;
  }

  public String getCover() {
    return cover;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Book() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.isbn);
    dest.writeString(this.title);
    dest.writeInt(this.price);
    dest.writeString(this.cover);
    dest.writeInt(this.quantity);
  }

  protected Book(Parcel in) {
    this.isbn = in.readString();
    this.title = in.readString();
    this.price = in.readInt();
    this.cover = in.readString();
    this.quantity = in.readInt();
  }

  public static final Creator<Book> CREATOR = new Creator<Book>() {
    public Book createFromParcel(Parcel source) {
      return new Book(source);
    }

    public Book[] newArray(int size) {
      return new Book[size];
    }
  };
}
