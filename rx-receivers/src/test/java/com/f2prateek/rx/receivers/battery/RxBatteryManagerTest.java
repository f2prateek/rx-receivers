package com.f2prateek.rx.receivers.battery;

import android.app.Application;
import android.content.Intent;
import android.os.BatteryManager;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import rx.observers.TestSubscriber;
import rx.functions.Func1;

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

    TestSubscriber<Integer> o = new TestSubscriber<>();
    //.map(BatteryChangedEvent.toPlugged())
    RxBatteryManager.changes(application).map(new Func1<BatteryChangedEvent, Integer>() {
            @Override public Integer call(BatteryChangedEvent changes) {
                return changes.plugged();
            }
        }).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
        .putExtra(BatteryManager.EXTRA_PLUGGED, BatteryManager.BATTERY_PLUGGED_AC);
    application.sendBroadcast(intent1);
    o.assertValues(BatteryManager.BATTERY_PLUGGED_AC);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
        .putExtra(BatteryManager.EXTRA_PLUGGED, BatteryManager.BATTERY_PLUGGED_USB);
    application.sendBroadcast(intent2);
    o.assertValues(BatteryManager.BATTERY_PLUGGED_AC, BatteryManager.BATTERY_PLUGGED_USB);
  }

  /** TODO */
  @Test public void present() {}

  /** TODO */
  @Test public void scale() {}

  @Test public void status() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.changes(application).map(new Func1<BatteryChangedEvent, Integer>() {
            @Override public Integer call(BatteryChangedEvent changes) {
                return changes.status();
            }
        }).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
        .putExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_CHARGING);
    application.sendBroadcast(intent1);
    o.assertValues(BatteryManager.BATTERY_STATUS_CHARGING);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
        .putExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
    application.sendBroadcast(intent2);
    o.assertValues(BatteryManager.BATTERY_STATUS_CHARGING, BatteryManager.BATTERY_STATUS_UNKNOWN);
  }

  /** TODO */
  @Test public void technology() {}

  /** TODO */
  @Test public void temperature() {}

  /** TODO */
  @Test public void voltage() {}
}
