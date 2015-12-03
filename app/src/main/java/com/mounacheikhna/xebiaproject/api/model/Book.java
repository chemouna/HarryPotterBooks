package com.mounacheikhna.xebiaproject.api.model;

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

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.isbn);
    dest.writeString(this.title);
    dest.writeInt(this.price);
    dest.writeString(this.cover);
  }

  public Book() {
  }

  protected Book(Parcel in) {
    this.isbn = in.readString();
    this.title = in.readString();
    this.price = in.readInt();
    this.cover = in.readString();
  }

  public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
    public Book createFromParcel(Parcel source) {
      return new Book(source);
    }

    public Book[] newArray(int size) {
      return new Book[size];
    }
  };
}
