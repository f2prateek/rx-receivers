package com.f2prateek.rx.preferences;

import com.f2prateek.rx.receivers.RxBroadcastReceiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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
      assertThat(e).hasMessage("state == null");
    }
  }
}
