package com.f2prateek.rx.receivers.wifi;

import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.f2prateek.rx.receivers.internal.Preconditions;

public final class NetworkStateChangedEvent {
  @CheckResult @NonNull //
  public static NetworkStateChangedEvent create(@NonNull NetworkInfo networkInfo,
      @Nullable String bssid, @Nullable WifiInfo wifiInfo) {
    Preconditions.checkNotNull(networkInfo, "networkInfo == null");
    return new NetworkStateChangedEvent(networkInfo, bssid, wifiInfo);
  }

  private final NetworkInfo networkInfo;
  private final String bssid;
  private final WifiInfo wifiInfo;

  private NetworkStateChangedEvent(@NonNull NetworkInfo networkInfo, @Nullable String bssid,
      @Nullable WifiInfo wifiInfo) {
    this.networkInfo = networkInfo;
    this.bssid = bssid;
    this.wifiInfo = wifiInfo;
  }

  public @NonNull NetworkInfo networkInfo() {
    return networkInfo;
  }

  public @Nullable String bssid() {
    return bssid;
  }

  public @Nullable WifiInfo wifiInfo() {
    return wifiInfo;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NetworkStateChangedEvent that = (NetworkStateChangedEvent) o;

    if (!networkInfo.equals(that.networkInfo)) return false;
    if (bssid != null ? !bssid.equals(that.bssid) : that.bssid != null) return false;
    return !(wifiInfo != null ? !wifiInfo.equals(that.wifiInfo) : that.wifiInfo != null);
  }

  @Override public int hashCode() {
    int result = networkInfo.hashCode();
    result = 31 * result + (bssid != null ? bssid.hashCode() : 0);
    result = 31 * result + (wifiInfo != null ? wifiInfo.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "NetworkStateChangedEvent{" +
        "networkInfo=" + networkInfo +
        ", bssid='" + bssid + '\'' +
        ", wifiInfo=" + wifiInfo +
        '}';
  }
}
