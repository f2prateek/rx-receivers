package com.f2prateek.rx.receivers.wifi;

import android.net.wifi.SupplicantState;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.f2prateek.rx.receivers.internal.Preconditions;

public final class SupplicantStateChangedEvent {
  @CheckResult @NonNull //
  public static SupplicantStateChangedEvent create(@NonNull SupplicantState newState, int error) {
    Preconditions.checkNotNull(newState, "newState == null");
    return new SupplicantStateChangedEvent(newState, error);
  }

  private final SupplicantState newState;
  private final int error;

  private SupplicantStateChangedEvent(@NonNull SupplicantState newState, int error) {
    this.newState = newState;
    this.error = error;
  }

  public @NonNull SupplicantState newState() {
    return newState;
  }

  public int error() {
    return error;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SupplicantStateChangedEvent that = (SupplicantStateChangedEvent) o;

    if (error != that.error) return false;
    return newState == that.newState;
  }

  @Override public int hashCode() {
    int result = newState != null ? newState.hashCode() : 0;
    result = 31 * result + error;
    return result;
  }

  @Override public String toString() {
    return "SupplicantStateChangedEvent{" +
        "newState=" + newState +
        ", error=" + error +
        '}';
  }
}
