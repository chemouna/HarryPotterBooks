package com.mounacheikhna.harrypotterbooks.ui.view.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mouna on 05/12/15.
 *
 * A custom adapter for {@link RecyclerView} to display headers and footers.
 */
public class HeaderFooterAdapter<T extends RecyclerView.Adapter>
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  /**
   * Defines available view type integers for headers and footers.
   *
   * How this works:
   * - Regular views use view types starting from 0, counting upwards
   * - Header views use view types starting from -1000, counting upwards
   * - Footer views use view types starting from -2000, counting upwards
   *
   * This means that you're safe as long as the base adapter doesn't use negative view types,
   * and as long as you have fewer than 1000 headers and footers
   */
  private static final int HEADER_VIEW_TYPE = -1000;
  private static final int FOOTER_VIEW_TYPE = -2000;
  private final T mBase;
  private final List<View> mHeaders = new ArrayList<>();
  private final List<View> mFooters = new ArrayList<>();

  /**
   * Constructor.
   *
   * @param base the adapter to wrap
   */
  public HeaderFooterAdapter(T base) {
    super();
    mBase = base;
  }

  /**
   * Gets the base adapter that this is wrapping.
   */
  public T getWrappedAdapter() {
    return mBase;
  }

  /**
   * Adds a header view.
   */
  public void addHeader(@NonNull View view) {
    if (view == null) {
      throw new IllegalArgumentException("You can't have a null header!");
    }
    mHeaders.add(view);
  }

  /**
   * Adds a footer view.
   */
  public void addFooter(@NonNull View view) {
    if (view == null) {
      throw new IllegalArgumentException("You can't have a null footer!");
    }
    mFooters.add(view);
  }

  /**
   * Toggles the visibility of the header views.
   */
  public void setHeaderVisibility(boolean shouldShow) {
    for (View header : mHeaders) {
      header.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }
  }

  /**
   * Toggles the visibility of the footer views.
   */
  public void setFooterVisibility(boolean shouldShow) {
    for (View footer : mFooters) {
      footer.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }
  }

  /**
   * @return the number of headers.
   */
  public int getHeaderCount() {
    return mHeaders.size();
  }

  /**
   * @return the number of footers.
   */
  public int getFooterCount() {
    return mFooters.size();
  }

  /**
   * Gets the indicated header, or null if it doesn't exist.
   */
  public View getHeader(int i) {
    return i < mHeaders.size() ? mHeaders.get(i) : null;
  }

  /**
   * Gets the indicated footer, or null if it doesn't exist.
   */
  public View getFooter(int i) {
    return i < mFooters.size() ? mFooters.get(i) : null;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    if (isHeader(viewType)) {
      int whichHeader = Math.abs(viewType - HEADER_VIEW_TYPE);
      View headerView = mHeaders.get(whichHeader);
      return new RecyclerView.ViewHolder(headerView) {
      };
    } else if (isFooter(viewType)) {
      int whichFooter = Math.abs(viewType - FOOTER_VIEW_TYPE);
      View footerView = mFooters.get(whichFooter);
      return new RecyclerView.ViewHolder(footerView) {
      };
    } else {
      return mBase.onCreateViewHolder(viewGroup, viewType);
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    if (position < mHeaders.size()) {
      // Headers don't need anything special

    } else if (position < mHeaders.size() + mBase.getItemCount()) {
      // This is a real position, not a header or footer. Bind it.
      mBase.onBindViewHolder(viewHolder, position - mHeaders.size());
    } else {
      // Footers don't need anything special
    }
  }

  @Override public int getItemViewType(int position) {
    if (position < mHeaders.size()) {
      return HEADER_VIEW_TYPE + position;
    } else if (position < (mHeaders.size() + mBase.getItemCount())) {
      return mBase.getItemViewType(position - mHeaders.size());
    } else {
      return FOOTER_VIEW_TYPE + position - mHeaders.size() - mBase.getItemCount();
    }
  }

  @Override public int getItemCount() {
    return mHeaders.size() + mBase.getItemCount() + mFooters.size();
  }

  private boolean isHeader(int viewType) {
    return viewType >= HEADER_VIEW_TYPE && viewType < (HEADER_VIEW_TYPE + mHeaders.size());
  }

  private boolean isFooter(int viewType) {
    return viewType >= FOOTER_VIEW_TYPE && viewType < (FOOTER_VIEW_TYPE + mFooters.size());
  }
}
