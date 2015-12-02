package com.mounacheikhna.xebiaproject;

import android.app.Application;
import android.content.Context;

/**
 * Created by mouna on 02/12/15.
 */
public class HenriPotierApp extends Application {
  private AppComponent mComponent;

  @Override public void onCreate() {
    super.onCreate();
    mComponent = AppComponent.Initializer.init(this);
    mComponent.injectApplication(this);
  }

  public static HenriPotierApp get(Context context) {
    return (HenriPotierApp) context.getApplicationContext();
  }

  public AppComponent getComponent() {
    return mComponent;
  }
}
