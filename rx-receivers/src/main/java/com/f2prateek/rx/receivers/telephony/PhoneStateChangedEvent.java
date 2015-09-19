package com.f2prateek.rx.receivers.telephony;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.f2prateek.rx.receivers.internal.Preconditions;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PhoneStateChangedEvent {
  @CheckResult @NonNull //
  public static PhoneStateChangedEvent create(@NonNull String state, @Nullable String phoneNumber) {
    Preconditions.checkNotNull(state, "state == null");
    return new AutoValue_PhoneStateChangedEvent(state, phoneNumber);
  }

  public abstract @NonNull String state();

  public abstract @Nullable String incomingNumber();
}