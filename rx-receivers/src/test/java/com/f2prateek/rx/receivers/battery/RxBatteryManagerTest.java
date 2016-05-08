package com.f2prateek.rx.receivers.battery;

import android.app.Application;
import android.content.Intent;
import android.os.BatteryManager;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import rx.observers.TestSubscriber;

@RunWith(RobolectricTestRunner.class) //
public class RxBatteryManagerTest {
  /** TODO */
  @Test public void changed() {}

  /** TODO */
  @Test public void health() {}

  /** TODO */
  @Test public void iconSmall() {}

  /** TODO */
  @Test public void level() {}

  @Test public void plugged() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<RxBatteryManager.Plugged> o = new TestSubscriber<>();
    RxBatteryManager.plugged(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
        .putExtra(BatteryManager.EXTRA_PLUGGED, BatteryManager.BATTERY_PLUGGED_AC);
    application.sendBroadcast(intent1);
    o.assertValues(RxBatteryManager.Plugged.BATTERY_PLUGGED_AC);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
        .putExtra(BatteryManager.EXTRA_PLUGGED, BatteryManager.BATTERY_PLUGGED_USB);
    application.sendBroadcast(intent2);
    o.assertValues(RxBatteryManager.Plugged.BATTERY_PLUGGED_AC, RxBatteryManager.Plugged.BATTERY_PLUGGED_USB);
  }

  /** TODO */
  @Test public void present() {}

  /** TODO */
  @Test public void scale() {}

  @Test public void status() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<RxBatteryManager.Status> o = new TestSubscriber<>();
    RxBatteryManager.status(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
        .putExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_CHARGING);
    application.sendBroadcast(intent1);
    o.assertValues(RxBatteryManager.Status.BATTERY_STATUS_CHARGING);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
        .putExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
    application.sendBroadcast(intent2);
    o.assertValues(RxBatteryManager.Status.BATTERY_STATUS_CHARGING, RxBatteryManager.Status.BATTERY_STATUS_UNKNOWN);
  }

  /** TODO */
  @Test public void technology() {}

  /** TODO */
  @Test public void temperature() {}

  /** TODO */
  @Test public void voltage() {}
}
