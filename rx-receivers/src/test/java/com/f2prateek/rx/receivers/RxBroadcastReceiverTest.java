package com.f2prateek.rx.receivers;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import com.f2prateek.rx.receivers.RxBroadcastReceiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import rx.Subscription;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class) //
public class RxBroadcastReceiverTest {
  @Test public void createWithNullThrows() {
    try {
      //noinspection ResourceType
      RxBroadcastReceiver.create(null, (IntentFilter) null);
      fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("context == null");
    }
  }

  @Test public void subscribe() {
    IntentFilter intentFilter = new IntentFilter("test_action");
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Intent> o = new TestSubscriber<>();
    Subscription subscription = RxBroadcastReceiver.create(application, intentFilter).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent("test_action").putExtra("foo", "bar");
    application.sendBroadcast(intent1);
    o.assertValues(intent1);

    Intent intent2 = new Intent("test_action").putExtra("bar", "baz");
    application.sendBroadcast(intent2);
    o.assertValues(intent1, intent2);

    Intent intent3 = new Intent("test_action_ignored");
    application.sendBroadcast(intent3);
    o.assertValues(intent1, intent2);

    Intent intent4 = new Intent("test_action").putExtra("bar", "baz");
    subscription.unsubscribe();
    application.sendBroadcast(intent4);
    o.assertValues(intent1, intent2);
  }
}
