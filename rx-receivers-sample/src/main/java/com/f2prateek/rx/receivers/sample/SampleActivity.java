package com.f2prateek.rx.receivers.sample;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.f2prateek.rx.receivers.telephony.PhoneStateChangedEvent;
import com.f2prateek.rx.receivers.telephony.RxTelephonyManager;
import com.f2prateek.rx.receivers.wifi.RxWifiManager;
import com.jakewharton.rxbinding.widget.RxTextView;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class SampleActivity extends Activity {

  @InjectView(R.id.phone_state) TextView phoneStateView;
  @InjectView(R.id.wifi_state) TextView wifiStateView;

  CompositeSubscription subscriptions = new CompositeSubscription();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Views
    setContentView(R.layout.sample_activity);
    ButterKnife.inject(this);

    Subscription s1 = RxTelephonyManager.phoneStateChanges(this) //
        .map(new Func1<PhoneStateChangedEvent, String>() {
          @Override public String call(PhoneStateChangedEvent event) {
            return event.toString();
          }
        }) //
        .startWith("waiting for change") //
        .map(prefix(getString(R.string.phone_state))) //
        .subscribe(RxTextView.text(phoneStateView));
    subscriptions.add(s1);

    Subscription s2 = RxWifiManager.wifiStateChanges(this) //
        .map(new Func1<Integer, String>() {
          @Override public String call(Integer integer) {
            switch (integer) {
              case WifiManager.WIFI_STATE_DISABLED:
                return "wifi disabled";
              case WifiManager.WIFI_STATE_DISABLING:
                return "wifi disabling";
              case WifiManager.WIFI_STATE_ENABLED:
                return "wifi enabled";
              case WifiManager.WIFI_STATE_ENABLING:
                return "enabling";
              default:
                return "unknown";
            }
          }
        }) //
        .map(prefix(getString(R.string.wifi_state))) //
        .subscribe(RxTextView.text(wifiStateView));
    subscriptions.add(s2);
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    subscriptions.unsubscribe();
  }

  static Func1<String, String> prefix(final String prefix) {
    return new Func1<String, String>() {
      @Override public String call(String s) {
        return prefix + ": " + s;
      }
    };
  }
}
