package com.mounacheikhna.xebiaproject.data;

import com.mounacheikhna.xebiaproject.api.henripotier.model.Book;
import com.mounacheikhna.xebiaproject.api.henripotier.model.Offer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mouna on 05/12/15.
 */
public class Cart {

  List<Book> mBooks = new ArrayList<>();

  public Cart() {
  }

  public Cart(List<Book> books) {
    mBooks = books;
  }

  public void addBook(Book book) {
    mBooks.add(book);
  }

  public List<Book> getBooks() {
    return mBooks;
  }

  public int getTotal() {
    int total = 0;
    for (Book b : mBooks) {
      total += (b.getQuantity() * b.getPrice());
    }
    return total;
  }

  public float getPrice() {
    float price = 0;
    if (mBooks != null) {
      int size = mBooks.size();
      for (int i = 0; i < size; ++i)
        price += mBooks.get(i).getPrice();
    }

    return price;
  }

  public OfferPrice getBestOffer(List<Offer> offers) {
    float price = getPrice();
    List<OfferPrice> bestOffers = new ArrayList<>();
    for (Offer offer : offers) {
      float bestPrice = 0;
      float promo = 0;
      switch (offer.getType()) {
        case Offer.TYPE_MINUS:
          bestPrice = price - offer.getValue();
          promo = -offer.getValue();
          break;
        case Offer.TYPE_PERCENTAGE:
          bestPrice = price * (1.0f - offer.getValue() / 100f);
          promo = -(price * offer.getValue() / 100f);
          break;
        case Offer.TYPE_SLICE:
          if (offer.getSliceValue() == 0) {
            bestPrice = price;
          } else {
            float offerValue = ((int) price / offer.getSliceValue()) * offer.getValue();
            promo = -offerValue;
            bestPrice = price - offerValue;
          }
          break;
      }
      bestOffers.add(new OfferPrice(bestPrice, promo, offer));
    }
    return Collections.min(bestOffers);
  }

  public static class OfferPrice implements Comparable<OfferPrice> {

    private float price;
    private float promo;
    private Offer offer;

    public OfferPrice(float price, float promo, Offer offer) {
      this.price = price;
      this.promo = promo;
      this.offer = offer;
    }

    public float getPrice() {
      return price;
    }

    public void setPrice(float price) {
      this.price = price;
    }

    public Offer getOffer() {
      return offer;
    }

    public void setOffer(Offer offer) {
      this.offer = offer;
    }

    public float getPromo() {
      return promo;
    }

    @Override public int compareTo(OfferPrice another) {
      if (getPrice() < another.getPrice()) {
        return -1;
      } else if (getPrice() == another.getPrice()) {
        return 0;
      } else {
        return 1;
      }
    }
  }
}

