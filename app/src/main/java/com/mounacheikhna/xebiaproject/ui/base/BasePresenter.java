package com.mounacheikhna.xebiaproject.ui.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by mouna on 02/12/15.
 */
abstract public class BasePresenter<T extends PresenterScreen> {

  protected CompositeSubscription mCompositeSubscription = new CompositeSubscription();
  protected T mView;

  public void bind(T view) {
    mView = view;
  }

  public void unbind() {
    mView = null;
  }

  public void onAttach(T view) {
    bind(view);
  }

  public void onDetach() {
    mCompositeSubscription.clear();
    unbind();
  }
}

