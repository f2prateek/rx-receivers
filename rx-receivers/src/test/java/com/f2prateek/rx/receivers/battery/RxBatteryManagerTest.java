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
  @Test public void changed() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.changed(application).map(new Func1<Intent, Integer>() {
      @Override public Integer call(Intent intent) {
        return intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
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

  @Test public void health() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.changes(application).map(new Func1<BatteryChangedEvent, Integer>() {
      @Override public Integer call(BatteryChangedEvent changes) {
        return changes.health();
      }
    }).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);
    application.sendBroadcast(intent1);
    o.assertValues(BatteryManager.BATTERY_HEALTH_UNKNOWN);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_COLD);
    application.sendBroadcast(intent2);
    o.assertValues(BatteryManager.BATTERY_HEALTH_UNKNOWN, BatteryManager.BATTERY_HEALTH_COLD);
  }

  @Test public void healthSimple() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.health(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);
    application.sendBroadcast(intent1);
    o.assertValues(BatteryManager.BATTERY_HEALTH_UNKNOWN);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_COLD);
    application.sendBroadcast(intent2);
    o.assertValues(BatteryManager.BATTERY_HEALTH_UNKNOWN, BatteryManager.BATTERY_HEALTH_COLD);
  }

  @Test public void smallIcon() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.changes(application).map(new Func1<BatteryChangedEvent, Integer>() {
      @Override public Integer call(BatteryChangedEvent changes) {
        return changes.smallIcon();
      }
    }).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_ICON_SMALL, 0);
    application.sendBroadcast(intent1);
    o.assertValues(0);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_ICON_SMALL, 1);
    application.sendBroadcast(intent2);
    o.assertValues(0, 1);
  }

  @Test public void smallIconSimple() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.smallIcon(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_ICON_SMALL, 0);
    application.sendBroadcast(intent1);
    o.assertValues(0);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_ICON_SMALL, 1);
    application.sendBroadcast(intent2);
    o.assertValues(0, 1);
  }

  @Test public void level() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.changes(application).map(new Func1<BatteryChangedEvent, Integer>() {
      @Override public Integer call(BatteryChangedEvent changes) {
        return changes.level();
      }
    }).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_LEVEL, 0);
    application.sendBroadcast(intent1);
    o.assertValues(0);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_LEVEL, 1);
    application.sendBroadcast(intent2);
    o.assertValues(0, 1);
  }

  @Test public void levelSimple() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.level(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_LEVEL, 0);
    application.sendBroadcast(intent1);
    o.assertValues(0);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_LEVEL, 1);
    application.sendBroadcast(intent2);
    o.assertValues(0, 1);
  }

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

  @Test public void pluggedSimple() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.plugged(application).subscribe(o);
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

  @Test public void present() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Boolean> o = new TestSubscriber<>();
    RxBatteryManager.changes(application).map(new Func1<BatteryChangedEvent, Boolean>() {
      @Override public Boolean call(BatteryChangedEvent changes) {
        return changes.present();
      }
    }).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_PRESENT, false);
    application.sendBroadcast(intent1);
    o.assertValues(false);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_PRESENT, true);
    application.sendBroadcast(intent2);
    o.assertValues(false, true);
  }

  @Test public void presentSimple() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Boolean> o = new TestSubscriber<>();
    RxBatteryManager.present(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_PRESENT, false);
    application.sendBroadcast(intent1);
    o.assertValues(false);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_PRESENT, true);
    application.sendBroadcast(intent2);
    o.assertValues(false, true);
  }

  @Test public void scale() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.changes(application).map(new Func1<BatteryChangedEvent, Integer>() {
      @Override public Integer call(BatteryChangedEvent changes) {
        return changes.scale();
      }
    }).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_SCALE, 0);
    application.sendBroadcast(intent1);
    o.assertValues(0);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_SCALE, 1);
    application.sendBroadcast(intent2);
    o.assertValues(0, 1);
  }

  @Test public void scaleSimple() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.scale(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_SCALE, 0);
    application.sendBroadcast(intent1);
    o.assertValues(0);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_SCALE, 1);
    application.sendBroadcast(intent2);
    o.assertValues(0, 1);
  }

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

  @Test public void statusSimple() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.status(application).subscribe(o);
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

  @Test public void technology() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<String> o = new TestSubscriber<>();
    RxBatteryManager.changes(application).map(new Func1<BatteryChangedEvent, String>() {
            @Override public String call(BatteryChangedEvent changes) {
                return changes.technology();
            }
        }).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
        .putExtra(BatteryManager.EXTRA_TECHNOLOGY, "");
    application.sendBroadcast(intent1);
    o.assertValues("");

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
        .putExtra(BatteryManager.EXTRA_TECHNOLOGY, "Li-ion");
    application.sendBroadcast(intent2);
    o.assertValues("", "Li-ion");
  }

  @Test public void technologySimple() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<String> o = new TestSubscriber<>();
    RxBatteryManager.technology(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_TECHNOLOGY, "");
    application.sendBroadcast(intent1);
    o.assertValues("");

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_TECHNOLOGY, "Li-ion");
    application.sendBroadcast(intent2);
    o.assertValues("", "Li-ion");
  }

  @Test public void temperature() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.changes(application).map(new Func1<BatteryChangedEvent, Integer>() {
      @Override public Integer call(BatteryChangedEvent changes) {
        return changes.temperature();
      }
    }).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
    application.sendBroadcast(intent1);
    o.assertValues(0);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_TEMPERATURE, 1);
    application.sendBroadcast(intent2);
    o.assertValues(0, 1);
  }

  @Test public void temperatureSimple() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.temperature(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
    application.sendBroadcast(intent1);
    o.assertValues(0);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_TEMPERATURE, 1);
    application.sendBroadcast(intent2);
    o.assertValues(0, 1);
  }

  @Test public void voltage() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.changes(application).map(new Func1<BatteryChangedEvent, Integer>() {
      @Override public Integer call(BatteryChangedEvent changes) {
        return changes.voltage();
      }
    }).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_VOLTAGE, 0);
    application.sendBroadcast(intent1);
    o.assertValues(0);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_VOLTAGE, 1);
    application.sendBroadcast(intent2);
    o.assertValues(0, 1);
  }

  @Test public void voltageSimple() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> o = new TestSubscriber<>();
    RxBatteryManager.voltage(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_VOLTAGE, 0);
    application.sendBroadcast(intent1);
    o.assertValues(0);

    Intent intent2 = new Intent(Intent.ACTION_BATTERY_CHANGED) //
      .putExtra(BatteryManager.EXTRA_VOLTAGE, 1);
    application.sendBroadcast(intent2);
    o.assertValues(0, 1);
  }
}
