package com.f2prateek.rx.receivers.wifi;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.f2prateek.rx.receivers.RxBroadcastReceiver;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static android.net.wifi.WifiManager.EXTRA_WIFI_STATE;
import static com.f2prateek.rx.receivers.internal.Preconditions.checkNotNull;

public final class RxWifiManager {
  private RxWifiManager() {
    throw new AssertionError("no instances");
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> wifiStateChanges(@NonNull final Context context) {
    checkNotNull(context, "context == null");
    IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
    return RxBroadcastReceiver.create(context, filter).map(new Function<Intent, Integer>() {
      @Override
      public Integer apply(@NonNull final Intent intent) {
        return intent.getIntExtra(EXTRA_WIFI_STATE, -1);
      }
    });
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<NetworkStateChangedEvent> //
  networkStateChanges(@NonNull final Context context) {
    checkNotNull(context, "context == null");
    IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
    return RxBroadcastReceiver.create(context, filter)
        .map(new Function<Intent, NetworkStateChangedEvent>() {
          @Override
          public NetworkStateChangedEvent apply(@NonNull final Intent intent) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            String bssid = intent.getStringExtra(WifiManager.EXTRA_BSSID);
            WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
            return NetworkStateChangedEvent.create(networkInfo, bssid, wifiInfo);
          }
        });
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Boolean> //
  supplicantConnectionChanges(@NonNull final Context context) {
    checkNotNull(context, "context == null");
    IntentFilter filter = new IntentFilter(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
    return RxBroadcastReceiver.create(context, filter).map(new Function<Intent, Boolean>() {
      @Override
      public Boolean apply(@NonNull final Intent intent) {
        return intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);
      }
    });
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<SupplicantStateChangedEvent> //
  supplicantStateChanges(@NonNull final Context context) {
    checkNotNull(context, "context == null");
    IntentFilter filter = new IntentFilter(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
    return RxBroadcastReceiver.create(context, filter)
        .map(new Function<Intent, SupplicantStateChangedEvent>() {
          @Override
          public SupplicantStateChangedEvent apply(@NonNull final Intent intent) {
            SupplicantState newState = intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
            int error = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, 0);
            return SupplicantStateChangedEvent.create(newState, error);
          }
        });
  }
}
