package com.mounacheikhna.harrypotterbooks.api;

import android.app.Application;
import android.net.Uri;
import com.mounacheikhna.harrypotterbooks.BuildConfig;
import com.mounacheikhna.harrypotterbooks.annotation.ApiClient;
import com.mounacheikhna.harrypotterbooks.annotation.NetworkInterceptors;
import com.mounacheikhna.harrypotterbooks.api.goodreads.GoodreadsApi;
import com.mounacheikhna.harrypotterbooks.api.goodreads.GoodreadsInterceptor;
import com.mounacheikhna.harrypotterbooks.api.harrypotter.HarryPotterBooksApi;
import com.squareup.moshi.Moshi;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;
import retrofit.MoshiConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.SimpleXmlConverterFactory;

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

  @Provides @Singleton @Named("RetrofitDefault") Retrofit provideRetrofit(
      @ApiClient OkHttpClient apiClient, Moshi moshi) {
    return new Retrofit.Builder().client(apiClient)
        .baseUrl(HttpUrl.parse(BuildConfig.HARRY_POTTER_BOOK_ENDPOINT_URL))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }

  @Provides @Singleton HarryPotterBooksApi providesSwapiApi(
      @Named("RetrofitDefault") Retrofit retrofit) {
    return retrofit.create(HarryPotterBooksApi.class);
  }

  /**
   * We need to create a new retrofit instance for goodreads api because it needs to hook in to
   * a different base url and uses an xml converter instead.
   */
  @Provides @Singleton @Named("RetrofitGoodreads") Retrofit provideGoodreadsRetrofit(
      @ApiClient OkHttpClient apiClient, GoodreadsInterceptor goodreadsInterceptor) {
    OkHttpClient goodreadsClient = apiClient.clone();
    goodreadsClient.interceptors().add(goodreadsInterceptor);
    return new Retrofit.Builder().client(goodreadsClient)
        .baseUrl(HttpUrl.parse(BuildConfig.GOOD_READ_ENDPOINT_URL))
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }

  @Provides @Singleton GoodreadsApi provideGoodreadsApi(
      @Named("RetrofitGoodreads") Retrofit retrofit) {
    return retrofit.create(GoodreadsApi.class);
  }

  @Provides @Singleton GoodreadsInterceptor provideGoodreadsInterceptor() {
    return new GoodreadsInterceptor();
  }

  @Provides @Singleton Picasso providePicasso(Application app, OkHttpClient client,
      @NetworkInterceptors List<Interceptor> networkInterceptors) {
    OkHttpClient okClient = client.clone();
    okClient.networkInterceptors().addAll(networkInterceptors);
    return new Picasso.Builder(app).downloader(new OkHttpDownloader(okClient))
        .listener(new Picasso.Listener() {
          @Override public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
          }
        })
        .build();
  }
}
