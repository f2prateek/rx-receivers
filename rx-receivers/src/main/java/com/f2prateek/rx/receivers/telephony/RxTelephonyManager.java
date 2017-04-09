package com.f2prateek.rx.receivers.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import com.f2prateek.rx.receivers.RxBroadcastReceiver;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static com.f2prateek.rx.receivers.internal.Preconditions.checkNotNull;

public final class RxTelephonyManager {
  private RxTelephonyManager() {
    throw new AssertionError("no instances");
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<PhoneStateChangedEvent> //
  phoneStateChanges(@NonNull final Context context) {
    checkNotNull(context, "context == null");
    IntentFilter filter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
    return RxBroadcastReceiver.create(context, filter)
        .map(new Function<Intent, PhoneStateChangedEvent>() {
          @Override
          public PhoneStateChangedEvent apply(@NonNull Intent intent) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            return PhoneStateChangedEvent.create(state, phoneNumber);
          }
        });
  }
}
