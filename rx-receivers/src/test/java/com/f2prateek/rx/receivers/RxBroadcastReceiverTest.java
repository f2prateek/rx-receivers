package com.f2prateek.rx.receivers;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class) //
public class RxBroadcastReceiverTest {
  @Test public void createWithNullThrows() {
    try {
      //noinspection ResourceType
      RxBroadcastReceiver.create(null, null);
      fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("context == null");
    }
  }

  @Test public void subscribe() {
    IntentFilter intentFilter = new IntentFilter("test_action");
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Intent> ts = RxBroadcastReceiver.create(application, intentFilter).test();
    ts.assertValues();

    Intent intent1 = new Intent("test_action").putExtra("foo", "bar");
    application.sendBroadcast(intent1);
    ts.assertValues(intent1);

    Intent intent2 = new Intent("test_action").putExtra("bar", "baz");
    application.sendBroadcast(intent2);
    ts.assertValues(intent1, intent2);

    Intent intent3 = new Intent("test_action_ignored");
    application.sendBroadcast(intent3);
    ts.assertValues(intent1, intent2);

    Intent intent4 = new Intent("test_action").putExtra("bar", "baz");
    ts.dispose();
    application.sendBroadcast(intent4);
    ts.assertValues(intent1, intent2);
  }
}
