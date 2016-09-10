package com.f2prateek.rx.receivers.battery;

import android.os.BatteryManager;

public enum BatteryHealth {
  COLD, DEAD, GOOD, OVER_VOLTAGE, OVERHEAT, UNSPECIFIED_FAILURE, UNKNOWN;

  static BatteryHealth of(int health) {
    switch (health) {
      case BatteryManager.BATTERY_HEALTH_COLD:
        return COLD;
      case BatteryManager.BATTERY_HEALTH_DEAD:
        return DEAD;
      case BatteryManager.BATTERY_HEALTH_GOOD:
        return GOOD;
      case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
        return OVER_VOLTAGE;
      case BatteryManager.BATTERY_HEALTH_OVERHEAT:
        return OVERHEAT;
      case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
        return UNSPECIFIED_FAILURE;
      default:
        return UNKNOWN;
    }
  }
}
