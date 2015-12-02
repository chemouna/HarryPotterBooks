package com.mounacheikhna.xebiaproject;

import com.mounacheikhna.xebiaproject.api.CoreApiModule;
import com.mounacheikhna.xebiaproject.api.DebugApiModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
@Singleton
@Component(modules = { AppModule.class, CoreApiModule.class, DebugApiModule.class })
public interface AppComponent {
  void injectApplication(HenriPotierApp starWarsApp);

  final class Initializer {
    private Initializer() {
    }

    static AppComponent init(HenriPotierApp app) {
      return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
    }
  }
}
