package com.f2prateek.rx.receivers.battery;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.f2prateek.rx.receivers.RxBroadcastReceiver;
import rx.functions.Func1;
import rx.Observable;
import static com.f2prateek.rx.receivers.internal.Preconditions.checkNotNull;

public class RxBatteryManager {
  private RxBatteryManager() {
    throw new AssertionError("no instances");
  }

  @CheckResult @NonNull //
  public static Observable<Intent> changed(@NonNull final Context context) {
    checkNotNull(context, "context == null");
    return RxBroadcastReceiver.create(context, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
  }

  @CheckResult @NonNull //
  public static Observable<BatteryChangedEvent> changes(@NonNull final Context context) {
    checkNotNull(context, "context == null");
    return changed(context).map(new Func1<Intent, BatteryChangedEvent>() {
      @Override public BatteryChangedEvent call(Intent intent) {
          return BatteryChangedEvent.builder()
              .health(intent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN))
              .smallIcon(intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, -1))
              .level(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1))
              .plugged(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1))
              .present(intent.getIntExtra(BatteryManager.EXTRA_PRESENT, -1))
              .scale(intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1))
              .status(intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN))
              .technology(intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY))
              .temperature(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1))
              .voltage(intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1))
              .build();
      }
    });
  }
}
