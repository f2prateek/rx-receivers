package com.f2prateek.rx.receivers.telephony;

import android.app.Application;
import android.content.Intent;
import com.f2prateek.rx.receivers.telephony.PhoneStateChangedEvent;
import com.f2prateek.rx.receivers.telephony.RxTelephonyManager;
import io.reactivex.observers.TestObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED;
import static android.telephony.TelephonyManager.EXTRA_INCOMING_NUMBER;
import static android.telephony.TelephonyManager.EXTRA_STATE;
import static android.telephony.TelephonyManager.EXTRA_STATE_IDLE;

@RunWith(RobolectricTestRunner.class) //
public class RxTelephonyManagerTest {
  @Test public void phoneStateChanges() {
    Application application = RuntimeEnvironment.application;

    final TestObserver<PhoneStateChangedEvent> o = new TestObserver<>();
    RxTelephonyManager.phoneStateChanges(application).subscribe(o);
    o.assertValues();

    Intent intent1 = new Intent(ACTION_PHONE_STATE_CHANGED) //
        .putExtra(EXTRA_STATE, EXTRA_INCOMING_NUMBER)
        .putExtra(EXTRA_INCOMING_NUMBER, "123-456-7890");
    application.sendBroadcast(intent1);
    PhoneStateChangedEvent event1 =
        PhoneStateChangedEvent.create(EXTRA_INCOMING_NUMBER, "123-456-7890");
    o.assertValues(event1);

    Intent intent2 = new Intent(ACTION_PHONE_STATE_CHANGED).putExtra(EXTRA_STATE, EXTRA_STATE_IDLE);
    application.sendBroadcast(intent2);
    PhoneStateChangedEvent event2 = PhoneStateChangedEvent.create(EXTRA_STATE_IDLE, null);
    o.assertValues(event1, event2);
  }
}
