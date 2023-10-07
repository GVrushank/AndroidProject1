package diamond.guidediamond.fffdiamondsapps.mixadproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static diamond.guidediamond.fffdiamondsapps.mixadproject.AdUnit.mInterstitialAd;
import static diamond.guidediamond.fffdiamondsapps.mixadproject.AdUnit.nonLoaded;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Ads {

    static OnDismiss onDismiss;
    public ProgressDialog dialog;

    public Ads(OnDismiss onDismiss) {
        this.onDismiss = onDismiss;
    }

    public Ads() {
    }

    public static void lodInt(Context context) {

        if (AdUnit.isAds) {

            MobileAds.initialize(context, initializationStatus -> {

            });

            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(context, AdUnit.G_Inter, adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            mInterstitialAd = interstitialAd;
                            AdUnit.isLoaded = true;
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.d(TAG, loadAdError.toString());
                            mInterstitialAd = null;
                        }
                    });

        }

    }

    public void showIntCall(Activity activity, boolean isReload) {

        if (AdUnit.isLoaded) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage("Loading");
            dialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, 2000);

            if (AdUnit.mInterstitialAd != null) {
                AdUnit.mInterstitialAd.show(activity);

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();

                        if (isReload) {
                            mInterstitialAd = null;
                            Ads.lodInt(activity);
                        }
                        onDismiss.onDismiss();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        mInterstitialAd = null;
                        onDismiss.onDismiss();
                    }
                });
            } else {
                onDismiss.onDismiss();
            }

        }else {
            dialog.setMessage("Showing Ads");
            dialog.show();

            nonLoaded = true;
        }


    }

}
