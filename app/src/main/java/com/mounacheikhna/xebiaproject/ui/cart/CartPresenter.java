package com.mounacheikhna.xebiaproject.ui.cart;

import com.mounacheikhna.xebiaproject.api.HenriPotierAPi;
import com.mounacheikhna.xebiaproject.ui.base.BasePresenter;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mouna on 05/12/15.
 */
@Singleton public class CartPresenter extends BasePresenter<CartScreen> {

  private HenriPotierAPi mApi;

  @Inject public CartPresenter(HenriPotierAPi api) {
    mApi = api;
  }
}
