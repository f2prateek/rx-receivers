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
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxActivity;
import io.reactivex.functions.Function;

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
        .subscribe(s -> {
          phoneStateView.setText(s);
        });

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
        .subscribe(s -> {
          wifiStateView.setText(s);
        });

    RxBatteryManager.batteryChanges(this)
        .compose(this.<BatteryState>bindUntilEvent(ActivityEvent.PAUSE))
        .map(Object::toString)
        .map(prefix(getString(R.string.battery_state))) //
        .subscribe(s -> {
          batteryStateView.setText(s);
        });
  }

  static Function<String, String> prefix(final String prefix) {
    return s -> prefix + ": " + s;
  }
}
