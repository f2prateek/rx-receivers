package com.f2prateek.rx.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposables;

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
    return Observable.create(new ObservableOnSubscribe<Intent>() {
      @Override
      public void subscribe(@NonNull final ObservableEmitter<Intent> e) throws Exception {
        final BroadcastReceiver receiver = new BroadcastReceiver() {
          @Override
          public void onReceive(Context context, Intent intent) {
            e.onNext(intent);
          }
        };
        context.registerReceiver(receiver, intentFilter);
        e.setDisposable(Disposables.fromRunnable(new Runnable() {
          @Override
          public void run() {
            context.unregisterReceiver(receiver);
          }
        }));
      }
    });
  }
}
