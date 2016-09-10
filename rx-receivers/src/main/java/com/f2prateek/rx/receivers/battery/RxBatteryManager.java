package com.f2prateek.rx.receivers.battery;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.f2prateek.rx.receivers.RxBroadcastReceiver;
import rx.Observable;
import rx.functions.Func1;

import static com.f2prateek.rx.receivers.internal.Preconditions.checkNotNull;

public class RxBatteryManager {
  private RxBatteryManager() {
    throw new AssertionError("no instances");
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<BatteryState> batteryChanges(@NonNull final Context context) {
    checkNotNull(context, "context == null");
    IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    return RxBroadcastReceiver.create(context, filter) //
        .map(new Func1<Intent, BatteryState>() {
          @Override public BatteryState call(Intent intent) {
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
            int iconSmall = intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, -1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            return BatteryState.create(BatteryHealth.of(health), iconSmall, level, plugged, present,
                scale, BatteryStatus.of(status), technology, temperature, voltage);
          }
        });
  }
}
