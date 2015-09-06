package com.f2prateek.rx.receivers.internal;

public final class Preconditions {
  public static void checkNotNull(Object o, String failureMessage) {
    if (o == null) {
      throw new NullPointerException(failureMessage);
    }
  }

  private Preconditions() {
    throw new AssertionError("no instances");
  }
}
