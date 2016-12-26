package com.f2prateek.rx.receivers.sample;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.f2prateek.rx.receivers.battery.BatteryState;
import com.f2prateek.rx.receivers.battery.RxBatteryManager;
import com.f2prateek.rx.receivers.telephony.PhoneStateChangedEvent;
import com.f2prateek.rx.receivers.telephony.RxTelephonyManager;
import com.f2prateek.rx.receivers.wifi.RxWifiManager;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.RxActivity;
import rx.functions.Func1;

public class SampleActivity extends RxActivity {
  @BindView(R.id.phone_state) TextView phoneStateView;
  @BindView(R.id.wifi_state) TextView wifiStateView;
  @BindView(R.id.battery_state) TextView batteryStateView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Setup views.
    setContentView(R.layout.sample_activity);
    ButterKnife.bind(this);

    // Bind views to events.
    RxTelephonyManager.phoneStateChanges(this)
        .compose(this.<PhoneStateChangedEvent>bindUntilEvent(ActivityEvent.PAUSE))
        .map(Object::toString) //
        .startWith("waiting for change") //
        .map(prefix(getString(R.string.phone_state))) //
        .subscribe(RxTextView.text(phoneStateView));

    RxWifiManager.wifiStateChanges(this)
        .compose(this.<Integer>bindUntilEvent(ActivityEvent.PAUSE))
        .map(integer -> {
          switch (integer) {
            case WifiManager.WIFI_STATE_DISABLED:
              return "wifi disabled";
            case WifiManager.WIFI_STATE_DISABLING:
              return "wifi disabling";
            case WifiManager.WIFI_STATE_ENABLED:
              return "wifi enabled";
            case WifiManager.WIFI_STATE_ENABLING:
              return "wifi enabling";
            default:
              return "unknown";
          }
        }) //
        .map(prefix(getString(R.string.wifi_state))) //
        .subscribe(RxTextView.text(wifiStateView));

    RxBatteryManager.batteryChanges(this)
        .compose(this.<BatteryState>bindUntilEvent(ActivityEvent.PAUSE))
        .map(Object::toString)
        .map(prefix(getString(R.string.battery_state))) //
        .subscribe(RxTextView.text(batteryStateView));
  }

  static Func1<String, String> prefix(final String prefix) {
    return s -> prefix + ": " + s;
  }
}
