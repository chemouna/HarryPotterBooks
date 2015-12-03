package com.mounacheikhna.xebiaproject.ui.buy;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import butterknife.Bind;
import com.mounacheikhna.xebiaproject.R;
import com.mounacheikhna.xebiaproject.transition.MophFabDialogHelper;

import static com.mounacheikhna.xebiaproject.util.ApiLevels.isAtLeastLollipop;

/**
 * Created by mouna on 03/12/15.
 *
 * We are using an activity here instead of a dialog to be able to use activity transitions.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP) public class BuyBook extends AppCompatActivity {

  @Bind(R.id.container) ViewGroup mContainer;

  @SuppressLint("NewApi") @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.buy_book);
    getWindow().getDecorView()
        .setBackgroundColor(ContextCompat.getColor(this, R.color.background_dark));
    if (isAtLeastLollipop()) {
      MophFabDialogHelper.setupSharedElementTransitions(this, mContainer,
          getResources().getDimensionPixelSize(R.dimen.dialog_corner));
      //TransitionManager.beginDelayedTransition(mContainer);
    }

    /*
    if(isAtLeastLollipop()) {
      setExitSharedElementCallback(new SharedElementCallback() {
        @Override
        public View onCreateSnapshotView(Context context, Parcelable snapshot) {
          // grab the saved fab snapshot and pass it to the below via a View
          View view = new View(context);
          final Bitmap snapshotBitmap = getSnapshot(snapshot);
          if (snapshotBitmap != null) {
            view.setBackground(new BitmapDrawable(context.getResources(), snapshotBitmap));
          }
          return view;
        }

        @Override
        public void onSharedElementStart(List<String> sharedElementNames,
            List<View> sharedElements,
            List<View> sharedElementSnapshots) {
          // grab the fab snapshot and fade it out/in (depending on if we are entering or exiting)
          for (int i = 0; i < sharedElements.size(); i++) {
            if (sharedElements.get(i) == mContainer) {
              View snapshot = sharedElementSnapshots.get(i);
              BitmapDrawable fabSnapshot = (BitmapDrawable) snapshot.getBackground();
              fabSnapshot.setBounds(0, 0, snapshot.getWidth(), snapshot.getHeight());
              mContainer.getOverlay().clear();
              mContainer.getOverlay().add(fabSnapshot);
              //if (!isDismissing) {
              // fab -> login: fade out the fab snapshot
              ObjectAnimator.ofInt(fabSnapshot, "alpha", 0).setDuration(100).start();
              */
/*} else {
            // login -> fab: fade in the fab snapshot toward the end of the transition
            fabSnapshot.setAlpha(0);
            ObjectAnimator fadeIn = ObjectAnimator.ofInt(fabSnapshot, "alpha", 255)
                .setDuration(150);
            fadeIn.setStartDelay(150);
            fadeIn.start();
          }*/    /*

              forceSharedElementLayout();
              break;
            }
          }
        }

        private Bitmap getSnapshot(Parcelable parcel) {
          if (parcel instanceof Bitmap) {
            return (Bitmap) parcel;
          } else if (parcel instanceof Bundle) {
            Bundle bundle = (Bundle) parcel;
            // see SharedElementCallback#onCaptureSharedElementSnapshot
            return (Bitmap) bundle.getParcelable("sharedElement:snapshot:bitmap");
          }
          return null;
        }
      });
    }
    */
  }

 /* private void forceSharedElementLayout() {
    int widthSpec = View.MeasureSpec.makeMeasureSpec(mContainer.getWidth(),
        View.MeasureSpec.EXACTLY);
    int heightSpec = View.MeasureSpec.makeMeasureSpec(mContainer.getHeight(),
        View.MeasureSpec.EXACTLY);
   mContainer.measure(widthSpec, heightSpec);
   mContainer.layout(mContainer.getLeft(),mContainer.getTop(),mContainer.getRight(),mContainer
        .getBottom());
  }*/
}
