package com.f2prateek.rx.receivers.battery;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.f2prateek.rx.receivers.RxBroadcastReceiver;
import rx.functions.Func1;
import rx.Observable;
import static com.f2prateek.rx.receivers.internal.Preconditions.checkNotNull;

public class RxBatteryManager {
  private RxBatteryManager() {
    throw new AssertionError("no instances");
  }

  /** TODO: Non-inner */
  public enum Action {
      ACTION_CHARGING(BatteryManager.ACTION_DISCHARGING),
      ACTION_DISCHARGING(BatteryManager.ACTION_DISCHARGING);

      private final String value;

      private Action(String s) {
          value = s;
      }

      @Override
      public String toString() {
          return this.value;
      }
  }

  /** TODO: Non-inner */
  public enum Health implements Parcelable {
      BATTERY_HEALTH_UNKNOWN(BatteryManager.BATTERY_HEALTH_UNKNOWN),
      BATTERY_HEALTH_GOOD(BatteryManager.BATTERY_HEALTH_GOOD),
      BATTERY_HEALTH_OVERHEAT(BatteryManager.BATTERY_HEALTH_OVERHEAT),
      BATTERY_HEALTH_DEAD(BatteryManager.BATTERY_HEALTH_DEAD),
      BATTERY_HEALTH_OVER_VOLTAGE(BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE),
      BATTERY_HEALTH_UNSPECIFIED_FAILURE(BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE),
      BATTERY_HEALTH_COLD(BatteryManager.BATTERY_HEALTH_COLD);

      private final int value;

      private Health(int v) {
          value = v;
      }

      @Override
      public int describeContents() {
          return 0;
      }

      @Override
      public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(name());
      }

      /** Implement the Parcelable interface */
      public static final Creator<Health> CREATOR =
          new Creator<Health>() {
              public Health createFromParcel(Parcel in) {
                  return Health.valueOf(in.readString());
              }
              public Health[] newArray(int size) {
                  return new Health[size];
              }
          };
  }

  /** TODO: Non-inner */
  public enum Plugged implements Parcelable {
      BATTERY_PLUGGED_WIRELESS(BatteryManager.BATTERY_PLUGGED_WIRELESS),
      BATTERY_PLUGGED_AC(BatteryManager.BATTERY_PLUGGED_AC),
      BATTERY_PLUGGED_USB(BatteryManager.BATTERY_PLUGGED_USB);

      private final int value;

      private Plugged(int v) {
          value = v;
      }

      @Override
      public int describeContents() {
          return 0;
      }

      @Override
      public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(name());
      }

      /** Implement the Parcelable interface */
      public static final Creator<Plugged> CREATOR =
          new Creator<Plugged>() {
              public Plugged createFromParcel(Parcel in) {
                  return Plugged.valueOf(in.readString());
              }
              public Plugged[] newArray(int size) {
                  return new Plugged[size];
              }
          };
  }

  /** TODO: Non-inner */
  public enum Property implements Parcelable {
      BATTERY_PROPERTY_CHARGE_COUNTER(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER),
      BATTERY_PROPERTY_CURRENT_AVERAGE(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE),
      BATTERY_PROPERTY_CAPACITY(BatteryManager.BATTERY_PROPERTY_CAPACITY),
      BATTERY_PROPERTY_CURRENT_NOW(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW),
      BATTERY_PROPERTY_ENERGY_COUNTER(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);

      private final int value;

      private Property(int v) {
          value = v;
      }

      @Override
      public int describeContents() {
          return 0;
      }

      @Override
      public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(name());
      }

      /** Implement the Parcelable interface */
      public static final Creator<Property> CREATOR =
          new Creator<Property>() {
              public Property createFromParcel(Parcel in) {
                  return Property.valueOf(in.readString());
              }
              public Property[] newArray(int size) {
                  return new Property[size];
              }
          };
  }

  /** TODO: Non-inner */
  public enum Status implements Parcelable {
      BATTERY_STATUS_DISCHARGING(BatteryManager.BATTERY_STATUS_DISCHARGING),
      BATTERY_STATUS_UNKNOWN(BatteryManager.BATTERY_STATUS_UNKNOWN),
      BATTERY_STATUS_CHARGING(BatteryManager.BATTERY_STATUS_CHARGING),
      BATTERY_STATUS_NOT_CHARGING(BatteryManager.BATTERY_STATUS_NOT_CHARGING),
      BATTERY_STATUS_FULL(BatteryManager.BATTERY_STATUS_FULL);

      private final int value;

      private Status(int v) {
          value = v;
      }

      @Override
      public int describeContents() {
          return 0;
      }

      @Override
      public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(name());
      }

      /** Implement the Parcelable interface */
      public static final Creator<Status> CREATOR =
          new Creator<Status>() {
              public Status createFromParcel(Parcel in) {
                  return Status.valueOf(in.readString());
              }
              public Status[] newArray(int size) {
                  return new Status[size];
              }
          };
  }

  /** TODO: Non-inner */
  public enum Extra {
      EXTRA_HEALTH(BatteryManager.EXTRA_HEALTH),
      EXTRA_ICON_SMALL(BatteryManager.EXTRA_ICON_SMALL),
      EXTRA_LEVEL(BatteryManager.EXTRA_LEVEL),
      EXTRA_PLUGGED(BatteryManager.EXTRA_PLUGGED),
      EXTRA_PRESENT(BatteryManager.EXTRA_PRESENT),
      EXTRA_SCALE(BatteryManager.EXTRA_SCALE),
      EXTRA_STATUS(BatteryManager.EXTRA_STATUS),
      EXTRA_TECHNOLOGY(BatteryManager.EXTRA_TECHNOLOGY),
      EXTRA_TEMPERATURE(BatteryManager.EXTRA_TEMPERATURE),
      EXTRA_VOLTAGE(BatteryManager.EXTRA_VOLTAGE);

      private final String value;

      private Extra(String v) {
          value = v;
      }

      @Override
      public String toString() {
          return this.value;
      }
  }

  @CheckResult @NonNull //
  public static Observable<Integer> changed(@NonNull final Context context, @NonNull final String key) {
    checkNotNull(context, "context == null");
    IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    return RxBroadcastReceiver.create(context, filter).map(new Func1<Intent, Integer>() {
      @Override public Integer call(Intent intent) {
        return intent.getIntExtra(key, -1);
      }
    });
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Health> health(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_HEALTH).map(new Func1<Integer, Health>() {
      @Override public Health call(Integer value) {
          return Health.values()[value];
      }
    });
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> iconSmall(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_ICON_SMALL);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> level(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_LEVEL);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Plugged> plugged(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_PLUGGED).map(new Func1<Integer, Plugged>() {
      @Override public Plugged call(Integer value) {
          return Plugged.values()[value];
      }
    });
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> present(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_PRESENT);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> scale(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_SCALE);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Status> status(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_STATUS).map(new Func1<Integer, Status>() {
      @Override public Status call(Integer value) {
          return Status.values()[value];
      }
    });
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> technology(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_TECHNOLOGY);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> temperature(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_TEMPERATURE);
  }

  /** TODO: docs. */
  @CheckResult @NonNull //
  public static Observable<Integer> voltage(@NonNull final Context context) {
    return changed(context, BatteryManager.EXTRA_VOLTAGE);
  }
}
