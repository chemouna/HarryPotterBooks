package com.mounacheikhna.harrypotterbooks.data.prefs;

import android.content.SharedPreferences;
import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * An adapter to save gson to shared preferences using rxpreferences library.
 */
public class GsonPreferenceAdapter<T> implements Preference.Adapter<T> {
  private final T defaultValue;
  private final Gson gson;
  private final SyntaxExceptionBehavior syntaxExceptionBehavior;
  private Class<T> clazz;

  public GsonPreferenceAdapter(Gson gson, Class<T> clazz, T defaultValue,
      SyntaxExceptionBehavior syntaxExceptionBehavior) {
    this.gson = gson;
    this.clazz = clazz;
    this.defaultValue = defaultValue;
    this.syntaxExceptionBehavior = syntaxExceptionBehavior;
  }

  public void delete(String key, SharedPreferences preferences) {
    preferences.edit().remove(key).apply();
  }

  public T get(String key, SharedPreferences preferences) {
    String str = preferences.getString(key, null);
    T value;
    if (str == null) {
      value = this.defaultValue;
      return value;
    }
    try {
      return this.gson.fromJson(str, this.clazz);
    } catch (JsonSyntaxException jsonSyntaxException) {
      switch (this.syntaxExceptionBehavior) {
        case NULL:
          return null;
        case DELETE:
          delete(key, preferences);
          break;
        case THROWN:
          throw jsonSyntaxException;
        default:
          throw new IllegalStateException("Unknown behavior: " + this.syntaxExceptionBehavior);
      }
    }
  }

  @Override public void set(String key, T value, SharedPreferences.Editor editor) {
    editor.putString(key, this.gson.toJson(value)).apply();
  }

  public enum SyntaxExceptionBehavior {
    DELETE,
    NULL,
    THROWN
  }
}