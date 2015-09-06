package com.f2prateek.rx.receivers.telephony;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.f2prateek.rx.receivers.internal.Preconditions;

public final class PhoneStateChangedEvent {
  @CheckResult @NonNull //
  public static PhoneStateChangedEvent create(@NonNull String state, @Nullable String phoneNumber) {
    Preconditions.checkNotNull(state, "state == null");
    return new PhoneStateChangedEvent(state, phoneNumber);
  }

  private final String state;
  private final String incomingNumber;

  private PhoneStateChangedEvent(@NonNull String state, @Nullable String incomingNumber) {
    this.state = state;
    this.incomingNumber = incomingNumber;
  }

  public @NonNull String state() {
    return state;
  }

  public @Nullable String incomingNumber() {
    return incomingNumber;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PhoneStateChangedEvent that = (PhoneStateChangedEvent) o;

    if (!state.equals(that.state)) return false;
    return !(incomingNumber != null ? !incomingNumber.equals(that.incomingNumber)
        : that.incomingNumber != null);
  }

  @Override public int hashCode() {
    int result = state.hashCode();
    result = 31 * result + (incomingNumber != null ? incomingNumber.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "PhoneStateChangedEvent{" +
        "state='" + state + '\'' +
        ", incomingNumber='" + incomingNumber + '\'' +
        '}';
  }
}
