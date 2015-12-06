package com.mounacheikhna.xebiaproject.api;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton public final class LoggingInterceptor implements Interceptor {

  @Inject public LoggingInterceptor() {
  }

  @Override public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();

    long startMs = System.currentTimeMillis();

    Response response = chain.proceed(request);

    long tookMs = System.currentTimeMillis() - startMs;

    return response;
  }

  private String prettyHeaders(Headers headers) {
    if (headers.size() == 0) return "";

    StringBuilder builder = new StringBuilder();
    builder.append("\n  Headers:");

    for (int i = 0; i < headers.size(); i++) {
      builder.append("\n    ").append(headers.name(i)).append(": ").append(headers.value(i));
    }

    return builder.toString();
  }
}
