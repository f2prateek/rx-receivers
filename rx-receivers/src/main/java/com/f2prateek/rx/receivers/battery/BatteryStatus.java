package com.f2prateek.rx.receivers.battery;

import android.os.BatteryManager;

public enum BatteryStatus {
  CHARGING, DISCHARGING, FULL, NOT_CHARGING, UNKNOWN;

  static BatteryStatus of(int status) {
    switch (status) {
      case BatteryManager.BATTERY_STATUS_CHARGING:
        return CHARGING;
      case BatteryManager.BATTERY_STATUS_DISCHARGING:
        return DISCHARGING;
      case BatteryManager.BATTERY_STATUS_FULL:
        return FULL;
      case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
        return NOT_CHARGING;
      default:
        return UNKNOWN;
    }
  }
}
