package com.mounacheikhna.xebiaproject.api.goodreads;

import com.mounacheikhna.xebiaproject.BuildConfig;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * Created by mouna on 06/12/15.
 *
 * An okhttp {@link Interceptor} to add parameter that are require in all requests to goodreads api.
 */
public class GoodreadsInterceptor implements Interceptor {

  @Override public Response intercept(Chain chain) throws IOException {
    HttpUrl url = chain.request()
        .httpUrl()
        .newBuilder()
        .setQueryParameter("key", BuildConfig.GOOD_READS_KEY)
        .setQueryParameter("secret", BuildConfig.GOOD_READS_SCECRET)
        .setQueryParameter("format", "xml")
        .build();
    final Request request = chain.request().newBuilder().url(url).build();
    return chain.proceed(request);
  }
}
