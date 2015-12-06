package com.mounacheikhna.xebiaproject.api.goodreads.model;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Created by mouna on 06/12/15.
 */
@Root(name = "GoodreadsResponse", strict = false)
public class GoodreadsResponse {

  @Element(name = "Request") Request request;

  @Element(name = "search", required = false) Search search;

  public Request getRequest() {
    return request;
  }

  public Search getSearch() {
    return search;
  }

  @Nullable public GoodreadsBook getFirstBook() {
    if (search == null || search.results == null || search.results.mGoodreadsWorks == null ||
        search.results.mGoodreadsWorks.size() == 0) {
      return null;
    }
    return search.results.mGoodreadsWorks.get(0).mGoodreadsBook;
  }

  @Nullable public GoodreadsWork getFirsWork() {
    if (search == null || search.results == null || search.results.mGoodreadsWorks == null ||
        search.results.mGoodreadsWorks.size() == 0) {
      return null;
    }
    return search.results.mGoodreadsWorks.get(0);
  }

  @Root(strict = false) public static class Request {
    @Element(name = "key") String key;

    @Element(name = "method") String method;

    @Element(name = "authentication") String authentication;

    public String getKey() {
      return key;
    }

    public String getMethod() {
      return method;
    }

    public String getAuthentication() {
      return authentication;
    }
  }

  @Root(strict = false) public static class Search {
    @Element(name = "results", required = false) Results results;
  }

  @Root(strict = false) public static class Results {
    @ElementList(name = "work", required = false, inline = true) ArrayList<GoodreadsWork>
        mGoodreadsWorks;
  }

}
