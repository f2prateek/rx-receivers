package com.f2prateek.rx.receivers.battery;

import android.app.Application;
import android.content.Intent;
import android.os.BatteryManager;
import io.reactivex.observers.TestObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class) //
public class RxBatteryManagerTest {
  @Test public void batteryStateChanges() {
    Application application = RuntimeEnvironment.application;

    final TestObserver<BatteryState> o = new TestObserver<>();
    RxBatteryManager.batteryChanges(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
        .putExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_COLD)
        .putExtra(BatteryManager.EXTRA_ICON_SMALL, 0x3def2)
        .putExtra(BatteryManager.EXTRA_LEVEL, 10)
        .putExtra(BatteryManager.EXTRA_PLUGGED, 0)
        .putExtra(BatteryManager.EXTRA_PRESENT, true)
        .putExtra(BatteryManager.EXTRA_SCALE, 100)
        .putExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_CHARGING)
        .putExtra(BatteryManager.EXTRA_TECHNOLOGY, "unknown")
        .putExtra(BatteryManager.EXTRA_TEMPERATURE, 40)
        .putExtra(BatteryManager.EXTRA_VOLTAGE, 10000);
    application.sendBroadcast(intent1);
    BatteryState event1 =
        BatteryState.create(BatteryHealth.COLD, 0x3def2, 10, 0, true, 100,
            BatteryStatus.CHARGING, "unknown", 40, 10000);
    o.assertValues(event1);
  }
}
