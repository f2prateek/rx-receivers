package com.f2prateek.rx.receivers.wifi;

import android.app.Application;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static android.net.wifi.WifiManager.ERROR_AUTHENTICATING;
import static android.net.wifi.WifiManager.EXTRA_NEW_STATE;
import static android.net.wifi.WifiManager.EXTRA_SUPPLICANT_ERROR;
import static android.net.wifi.WifiManager.EXTRA_WIFI_STATE;
import static android.net.wifi.WifiManager.NETWORK_STATE_CHANGED_ACTION;
import static android.net.wifi.WifiManager.WIFI_STATE_CHANGED_ACTION;
import static android.net.wifi.WifiManager.WIFI_STATE_DISABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_UNKNOWN;

@RunWith(RobolectricTestRunner.class) //
public class RxWifiManagerTest {
  @Test public void wifiStateChanges() {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Integer> ts = RxWifiManager.wifiStateChanges(application).test();
    ts.assertValues();

    Intent intent1 = new Intent(WIFI_STATE_CHANGED_ACTION) //
        .putExtra(EXTRA_WIFI_STATE, WIFI_STATE_DISABLED);
    application.sendBroadcast(intent1);
    ts.assertValues(WIFI_STATE_DISABLED);

    Intent intent2 = new Intent(WIFI_STATE_CHANGED_ACTION) //
        .putExtra(EXTRA_WIFI_STATE, WIFI_STATE_UNKNOWN);
    application.sendBroadcast(intent2);
    ts.assertValues(WIFI_STATE_DISABLED, WIFI_STATE_UNKNOWN);
  }

  @SuppressWarnings("ResourceType") @Test //
  public void networkStateChanges() throws IllegalAccessException, InstantiationException {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<NetworkStateChangedEvent> ts =
        RxWifiManager.networkStateChanges(application).test();
    ts.assertValues();

    NetworkInfo networkInfo1 = NetworkInfo.class.newInstance();
    WifiInfo wifiInfo1 = WifiInfo.class.newInstance();
    Intent intent1 = new Intent(NETWORK_STATE_CHANGED_ACTION) //
        .putExtra(WifiManager.EXTRA_NETWORK_INFO, networkInfo1)
        .putExtra(WifiManager.EXTRA_BSSID, "foo")
        .putExtra(WifiManager.EXTRA_WIFI_INFO, wifiInfo1);
    application.sendBroadcast(intent1);
    NetworkStateChangedEvent event1 =
        NetworkStateChangedEvent.create(networkInfo1, "foo", wifiInfo1);
    ts.assertValues(event1);

    NetworkInfo networkInfo2 = NetworkInfo.class.newInstance();
    Intent intent2 = new Intent(NETWORK_STATE_CHANGED_ACTION) //
        .putExtra(WifiManager.EXTRA_NETWORK_INFO, networkInfo2);
    application.sendBroadcast(intent2);
    NetworkStateChangedEvent event2 = NetworkStateChangedEvent.create(networkInfo2, null, null);
    ts.assertValues(event1, event2);
  }

  @SuppressWarnings("ResourceType") @Test //
  public void supplicantStateChanges() throws IllegalAccessException, InstantiationException {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<SupplicantStateChangedEvent> ts =
        RxWifiManager.supplicantStateChanges(application).test();
    ts.assertValues();

    Intent intent1 = new Intent(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION) //
        .putExtra(EXTRA_NEW_STATE, (Parcelable) SupplicantState.INACTIVE)
        .putExtra(EXTRA_SUPPLICANT_ERROR, ERROR_AUTHENTICATING);
    application.sendBroadcast(intent1);
    SupplicantStateChangedEvent event1 =
        SupplicantStateChangedEvent.create(SupplicantState.INACTIVE, ERROR_AUTHENTICATING);
    ts.assertValues(event1);

    Intent intent2 = new Intent(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION) //
        .putExtra(EXTRA_NEW_STATE, (Parcelable) SupplicantState.ASSOCIATING)
        .putExtra(EXTRA_SUPPLICANT_ERROR, -1);
    application.sendBroadcast(intent2);
    SupplicantStateChangedEvent event2 =
        SupplicantStateChangedEvent.create(SupplicantState.ASSOCIATING, -1);
    ts.assertValues(event1, event2);
  }

  @SuppressWarnings("ResourceType") @Test //
  public void supplicantConnectionChanges() throws IllegalAccessException, InstantiationException {
    Application application = RuntimeEnvironment.application;

    TestSubscriber<Boolean> ts = RxWifiManager.supplicantConnectionChanges(application).test();
    ts.assertValues();

    Intent intent1 = new Intent(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION) //
        .putExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, true);
    application.sendBroadcast(intent1);
    ts.assertValues(true);

    Intent intent2 = new Intent(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION) //
        .putExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);
    application.sendBroadcast(intent2);
    ts.assertValues(true, false);
  }
}
