package com.f2prateek.rx.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

import static com.f2prateek.rx.receivers.internal.Preconditions.checkNotNull;

public final class RxBroadcastReceiver {
  private RxBroadcastReceiver() {
    throw new AssertionError("no instances");
  }

  @CheckResult @NonNull //
  public static Observable<Intent> create(@NonNull final Context context,
      @NonNull final IntentFilter intentFilter) {
    checkNotNull(context, "context == null");
    checkNotNull(intentFilter, "intentFilter == null");
    return Observable.create(new Observable.OnSubscribe<Intent>() {
      @Override public void call(final Subscriber<? super Intent> subscriber) {
        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
          @Override public void onReceive(Context context, Intent intent) {
            subscriber.onNext(intent);
          }
        };

        context.registerReceiver(broadcastReceiver, intentFilter);

        subscriber.add(Subscriptions.create(new Action0() {
          @Override public void call() {
            context.unregisterReceiver(broadcastReceiver);
          }
        }));
      }
    });
  }
}
