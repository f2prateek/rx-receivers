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
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

import static com.f2prateek.rx.receivers.internal.Preconditions.checkNotNull;

public final class RxBroadcastReceiver {
  private RxBroadcastReceiver() {
    throw new AssertionError("no instances");
  }

  /** TODO: docs. */
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

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Intent> create(@NonNull final Context context, @NonNull final String action) {
    checkNotNull(context, "context == null");
    checkNotNull(action, "action == null");
    return create(context, new IntentFilter(action));
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> create(@NonNull final Context context, @NonNull final String action, @NonNull final String extra, @NonNull final int defValue) {
    checkNotNull(context, "context == null");
    checkNotNull(action, "action == null");
    checkNotNull(extra, "extra == null");
    checkNotNull(defValue, "defValue == null");
    return create(context, new IntentFilter(action)).map(new Func1<Intent, Integer>() {
      @Override public Integer call(Intent intent) {
        return intent.getIntExtra(extra, defValue);
      }
    });
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Boolean> create(@NonNull final Context context, @NonNull final String action, @NonNull final String extra, @NonNull final boolean defValue) {
    checkNotNull(context, "context == null");
    checkNotNull(action, "action == null");
    checkNotNull(extra, "extra == null");
    checkNotNull(defValue, "defValue == null");
    return create(context, new IntentFilter(action)).map(new Func1<Intent, Boolean>() {
      @Override public Boolean call(Intent intent) {
        return intent.getBooleanExtra(extra, defValue);
      }
    });
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<String> createString(@NonNull final Context context, @NonNull final String action, @NonNull final String extra) {
    checkNotNull(context, "context == null");
    checkNotNull(action, "action == null");
    checkNotNull(extra, "extra == null");
    return create(context, new IntentFilter(action)).map(new Func1<Intent, String>() {
      @Override public String call(Intent intent) {
        return intent.getStringExtra(extra);
      }
    });
  }
}
