package com.mounacheikhna.harrypotterbooks;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mounacheikhna.harrypotterbooks.annotation.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class AppModule {
  private final Application application;

  public AppModule(Application application) {
    this.application = application;
  }

  @Provides @Singleton public Application application() {
    return application;
  }

  @Provides @ApplicationContext public Context provideApplicationContext() {
    return application.getApplicationContext();
  }

  @Provides @Singleton SharedPreferences provideSharedPreferences(
      @ApplicationContext Context appContext) {
    return appContext.getSharedPreferences("harry-potter-books", Context.MODE_PRIVATE);
  }

  @Provides @Singleton RxSharedPreferences provideRxSharedPreferences(SharedPreferences prefs) {
    return RxSharedPreferences.create(prefs);
  }

  @Provides @Singleton Gson provideGson() {
    return new GsonBuilder().create();
  }
}
