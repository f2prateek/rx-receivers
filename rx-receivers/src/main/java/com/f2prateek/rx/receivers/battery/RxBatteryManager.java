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
  public static Observable<Integer> changed(@NonNull final Context context, @NonNull final String key) {
    checkNotNull(context, "context == null");
    IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    return RxBroadcastReceiver.create(context, filter).map(new Func1<Intent, Integer>() {
      @Override public Integer call(Intent intent) {
        return intent.getIntExtra(key, -1);
      }
    });
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> health(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_HEALTH);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> iconSmall(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_ICON_SMALL);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> level(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_LEVEL);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> plugged(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_PLUGGED);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> present(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_PRESENT);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> scale(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_SCALE);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> status(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_STATUS);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> technology(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_TECHNOLOGY);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> temperature(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_TEMPERATURE);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> voltage(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_VOLTAGE);
  }
}
