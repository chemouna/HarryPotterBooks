package com.mounacheikhna.xebiaproject;

import com.facebook.stetho.Stetho;
import rx.plugins.DebugHook;
import rx.plugins.DebugNotification;
import rx.plugins.DebugNotificationListener;
import rx.plugins.RxJavaPlugins;
import timber.log.Timber;

public class DebugHenriPotierApp extends HenriPotierApp {

  @Override public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    Timber.plant(new Timber.DebugTree());
    setupRxJavaDebug();
  }

  @SuppressWarnings("unchecked") private void setupRxJavaDebug() {
    RxJavaPlugins.getInstance()
        .registerObservableExecutionHook(new DebugHook(new DebugNotificationListener() {
          @Override public Object onNext(DebugNotification n) {
            Timber.v("DebugHook - onNext with value %s from op : %s ", n.getValue(), n.getFrom());
            return super.onNext(n);
          }

          @Override public Object start(DebugNotification n) {
            //Timber.v("DebugHook - start of "+ n.getFrom());
            return super.start(n);
          }

          @Override public void complete(Object context) {
            super.complete(context);
          }

          @Override public void error(Object context, Throwable e) {
            super.error(context, e);
            Timber.v("DebugHook - error event e : %s ", e.getCause());
          }
        }));
  }
}
