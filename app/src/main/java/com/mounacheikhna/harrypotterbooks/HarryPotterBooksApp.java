package com.mounacheikhna.harrypotterbooks;

import android.app.Application;
import android.content.Context;

/**
 * Created by mouna on 02/12/15.
 */
public class HarryPotterBooksApp extends Application {
  private AppComponent mComponent;

  @Override public void onCreate() {
    super.onCreate();
    mComponent = AppComponent.Initializer.init(this);
    mComponent.injectApplication(this);
  }

  public static HarryPotterBooksApp get(Context context) {
    return (HarryPotterBooksApp) context.getApplicationContext();
  }

  public AppComponent getComponent() {
    return mComponent;
  }
}
