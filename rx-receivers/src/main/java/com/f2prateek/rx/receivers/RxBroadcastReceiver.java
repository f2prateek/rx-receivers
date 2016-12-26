package com.f2prateek.rx.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.functions.Cancellable;

import static com.f2prateek.rx.receivers.internal.Preconditions.checkNotNull;

public final class RxBroadcastReceiver {
  private RxBroadcastReceiver() {
    throw new AssertionError("no instances");
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Flowable<Intent> create(@NonNull final Context context,
      @NonNull final IntentFilter intentFilter) {
    checkNotNull(context, "context == null");
    checkNotNull(intentFilter, "intentFilter == null");
    return Flowable.create(new FlowableOnSubscribe<Intent>() {
      @Override public void subscribe(final FlowableEmitter<Intent> emitter) throws Exception {
        final BroadcastReceiver receiver = new BroadcastReceiver() {
          @Override public void onReceive(Context context, Intent intent) {
            emitter.onNext(intent);
          }
        };

        context.registerReceiver(receiver, intentFilter);

        emitter.setCancellable(new Cancellable() {
          @Override public void cancel() throws Exception {
            context.unregisterReceiver(receiver);
          }
        });
      }
    }, BackpressureStrategy.BUFFER);
  }
}
