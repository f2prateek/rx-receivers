package com.f2prateek.rx.receivers.wifi;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.f2prateek.rx.receivers.RxBroadcastReceiver;
import com.f2prateek.rx.receivers.internal.Preconditions;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static android.net.wifi.WifiManager.EXTRA_WIFI_STATE;
import static com.f2prateek.rx.receivers.internal.Preconditions.checkNotNull;

public final class RxWifiManager {
  private RxWifiManager() {
    throw new AssertionError("no instances");
  }

  public enum Action {
      ACTION_PICK_WIFI_NETWORK(WifiManager.ACTION_PICK_WIFI_NETWORK),
      ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE(WifiManager.ACTION_PICK_WIFI_NETWORK),
      NETWORK_IDS_CHANGED_ACTION(WifiManager.NETWORK_IDS_CHANGED_ACTION),
      NETWORK_STATE_CHANGED_ACTION(WifiManager.NETWORK_STATE_CHANGED_ACTION),
      RSSI_CHANGED_ACTION(WifiManager.RSSI_CHANGED_ACTION),
      SCAN_RESULTS_AVAILABLE_ACTION(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION),
      SUPPLICANT_CONNECTION_CHANGE_ACTION(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION),
      SUPPLICANT_STATE_CHANGED_ACTION(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION),
      WIFI_STATE_CHANGED_ACTION(WifiManager.WIFI_STATE_CHANGED_ACTION);

      private final String value;

      private Action(String s) {
          value = s;
      }

      @Override
      public String toString() {
          return this.value;
      }
  }

  public enum Extra {
      EXTRA_BSSID(WifiManager.EXTRA_BSSID),
      EXTRA_NETWORK_INFO(WifiManager.EXTRA_NETWORK_INFO),
      EXTRA_NEW_RSSI(WifiManager.EXTRA_NEW_RSSI),
      EXTRA_NEW_STATE(WifiManager.EXTRA_NEW_STATE),
      EXTRA_PREVIOUS_WIFI_STATE(WifiManager.EXTRA_PREVIOUS_WIFI_STATE),
      EXTRA_RESULTS_UPDATED(WifiManager.EXTRA_RESULTS_UPDATED),
      EXTRA_SUPPLICANT_CONNECTED(WifiManager.EXTRA_SUPPLICANT_CONNECTED),
      EXTRA_SUPPLICANT_ERROR(WifiManager.EXTRA_SUPPLICANT_ERROR),
      EXTRA_WIFI_INFO(WifiManager.EXTRA_WIFI_INFO),
      EXTRA_WIFI_STATE(WifiManager.EXTRA_WIFI_STATE);

      final String value;

      private Extra(String s) {
          value = s;
      }

      @Override
      public String toString() {
          return this.value;
      }

      public String value() {
          return this.value;
      }
  }

  public enum Mode {
      WIFI_MODE_FULL(WifiManager.WIFI_MODE_FULL),
      WIFI_MODE_SCAN_ONLY(WifiManager.WIFI_MODE_SCAN_ONLY),
      WIFI_MODE_FULL_HIGH_PERF(WifiManager.WIFI_MODE_FULL_HIGH_PERF);

      private final int value;

      private Mode(int value) {
          this.value = value;
      }

      public int value() {
          return value;
      }
  }

  public enum State implements Parcelable {
      WIFI_STATE_DISABLING(WifiManager.WIFI_STATE_DISABLING),
      WIFI_STATE_DISABLED(WifiManager.WIFI_STATE_DISABLED),
      WIFI_STATE_ENABLED(WifiManager.WIFI_STATE_ENABLED),
      WIFI_STATE_ENABLING(WifiManager.WIFI_STATE_ENABLING),
      WIFI_STATE_UNKNOWN(WifiManager.WIFI_STATE_UNKNOWN);

      private final int value;

      private State(int value) {
          this.value = value;
      }

      public int value() {
          return value;
      }

      @Override
      public int describeContents() {
          return 0;
      }

      @Override
      public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(name());
      }

      /** Implement the Parcelable interface */
      public static final Creator<State> CREATOR =
          new Creator<State>() {
              public State createFromParcel(Parcel in) {
                  return State.valueOf(in.readString());
              }
              public State[] newArray(int size) {
                  return new State[size];
              }
          };
  }

  public enum Reason {
      ERROR_AUTHENTICATING(WifiManager.ERROR_AUTHENTICATING),
      WPS_OVERLAP_ERROR(WifiManager.WPS_OVERLAP_ERROR),
      WPS_WEP_PROHIBITED(WifiManager.WPS_WEP_PROHIBITED),
      WPS_TKIP_ONLY_PROHIBITED(WifiManager.WPS_TKIP_ONLY_PROHIBITED),
      WPS_AUTH_FAILURE(WifiManager.WPS_AUTH_FAILURE),
      WPS_TIMED_OUT(WifiManager.WPS_TIMED_OUT);

      private final int value;

      private Reason(int value) {
          this.value = value;
      }

      public int value() {
          return value;
      }
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<State> wifiStateChanges(@NonNull final Context context) {
    checkNotNull(context, "context == null");
    IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
    return RxBroadcastReceiver.create(context, filter).map(new Func1<Intent, Integer>() {
      @Override public Integer call(Intent intent) {
        return intent.getIntExtra(EXTRA_WIFI_STATE, -1);
      }
    }).map(new Func1<Integer, State>() {
      @Override public State call(Integer state) {
          return State.values()[state];
      }
    });
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Action1<? super Boolean> wifiState(@NonNull final WifiManager wifiManager) {
    checkNotNull(wifiManager, "wifiManager == null");
    return new Action1<Boolean>() {
      @Override public void call(Boolean enabled) {
        //noinspection ResourceType
        wifiManager.setWifiEnabled(enabled);
      }
    };
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<NetworkStateChangedEvent> //
  networkStateChanges(@NonNull final Context context) {
    checkNotNull(context, "context == null");
    IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
    return RxBroadcastReceiver.create(context, filter)
        .map(new Func1<Intent, NetworkStateChangedEvent>() {
          @Override public NetworkStateChangedEvent call(Intent intent) {
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
    return RxBroadcastReceiver.create(context, filter).map(new Func1<Intent, Boolean>() {
      @Override public Boolean call(Intent intent) {
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
        .map(new Func1<Intent, SupplicantStateChangedEvent>() {
          @Override public SupplicantStateChangedEvent call(Intent intent) {
            SupplicantState newState = intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
            int error = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, 0);
            return SupplicantStateChangedEvent.create(newState, error);
          }
        });
  }
}
