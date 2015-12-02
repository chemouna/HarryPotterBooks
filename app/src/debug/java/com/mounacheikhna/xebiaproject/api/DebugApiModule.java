package com.mounacheikhna.xebiaproject.api;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.mounacheikhna.xebiaproject.annotation.NetworkInterceptors;
import com.squareup.okhttp.Interceptor;
import dagger.Module;
import dagger.Provides;
import java.util.Arrays;
import java.util.List;
import javax.inject.Singleton;

@Module
public class DebugApiModule {

  /**
   * We need logging and stetho only for debug builds since we don't want information
   * about server api to be logged on release builds.
   */
  @Provides @Singleton @NetworkInterceptors List<Interceptor> provideNetworkInterceptors() {
    return Arrays.asList(new StethoInterceptor(), new LoggingInterceptor());
  }

}
