package com.mounacheikhna.xebiaproject.api;

import android.app.Application;
import android.net.Uri;
import com.mounacheikhna.xebiaproject.BuildConfig;
import com.mounacheikhna.xebiaproject.annotation.ApiClient;
import com.mounacheikhna.xebiaproject.annotation.NetworkInterceptors;
import com.squareup.moshi.Moshi;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Singleton;
import retrofit.MoshiConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import timber.log.Timber;

/**
 * Created by cheikhnamouna on 11/21/15.
 *
 * Core module with main api dependencies.
 */
@Module public class CoreApiModule {

  @Provides @Singleton Moshi provideMoshi() {
    return new Moshi.Builder().build();
  }

  @Provides @Singleton @ApiClient OkHttpClient provideApiClient(OkHttpClient client,
      @NetworkInterceptors List<Interceptor> networkInterceptors) {
    OkHttpClient okClient = client.clone();
    okClient.networkInterceptors().addAll(networkInterceptors);
    return okClient;
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient() {
    return new OkHttpClient();
  }

  @Provides @Singleton Retrofit provideRetrofit(@ApiClient OkHttpClient apiClient, Moshi moshi) {
    return new Retrofit.Builder().client(apiClient)
        .baseUrl(HttpUrl.parse(BuildConfig.HENRI_POTIER_ENDPOINT_URL))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }

  @Provides @Singleton HenriPotierAPi providesSwapiApi(Retrofit retrofit) {
    return retrofit.create(HenriPotierAPi.class);
  }

  @Provides @Singleton Picasso providePicasso(Application app, OkHttpClient client,
      @NetworkInterceptors List<Interceptor> networkInterceptors) {
    OkHttpClient okClient = client.clone();
    okClient.networkInterceptors().addAll(networkInterceptors);
    return new Picasso.Builder(app).downloader(new OkHttpDownloader(okClient))
        .listener(new Picasso.Listener() {
          @Override public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
            Timber.e("Failed to load image: %s, cause : %s", uri, e);
          }
        })
        .build();
  }
}
