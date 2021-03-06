package com.nguyen.wifibruteforce.model;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.nguyen.wifibruteforce.R;
import com.nguyen.wifibruteforce.Utils;

import java.util.Comparator;

public class WifiDetail {
    private String name;
    private String BSSID;
    private int signalLevel;    //0 - 4 level
    private int RSSI;   //signal strength (dBm)
    private int signalIcon;
    private  String capabilities;   //WPA, WPA2, etc...
    private String IP;

    public WifiDetail(String name, String BSSID, int signalLevel, int RSSI, int signalIcon, String capabilities, String IP) {
        this.name = name;
        this.BSSID = BSSID;
        this.signalLevel = signalLevel;
        this.RSSI = RSSI;
        this.signalIcon = signalIcon;
        this.capabilities = capabilities;
        this.IP = IP;
    }

    public WifiDetail(String name, String BSSID, int signalLevel, int RSSI, int signalIcon, String capabilities) {
        this.name = name;
        this.BSSID = BSSID;
        this.signalLevel = signalLevel;
        this.RSSI = RSSI;
        this.signalIcon = signalIcon;
        this.capabilities = capabilities;
    }

    public WifiDetail() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Utils.convertSSID(name);
    }

    public int getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(int signalLevel) {
        int lvl = 5;
        int calculatedLvl = WifiManager.calculateSignalLevel(signalLevel, lvl);
        this.signalLevel = calculatedLvl;
//        this.signalLevel = signalLevel;
    }
    public void setSignalLevel(ScanResult result) {
        int lvl = 5;
        int calculatedLvl = WifiManager.calculateSignalLevel(result.level, lvl);
        this.signalLevel = calculatedLvl;
    }

    public int getSignalIcon() {
        return signalIcon;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public int getRSSI() {
        return RSSI;
    }

    public void setRSSI(int RSSI) {
        this.RSSI = RSSI;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
//        InetAddress inetAddress = IP;
        this.IP = "IP: "+IP;
    }

    public void setSignalIcon() {
        switch (getSignalLevel()) {
            case 0:
                this.signalIcon = R.drawable.ic_signal_wifi_0_bar_grey_300_48dp;
            case 1:
                this.signalIcon = R.drawable.ic_signal_wifi_1_bar_red_a700_48dp;
                break;
            case 2:
                this.signalIcon = R.drawable.ic_signal_wifi_2_bar_orange_a700_48dp;
                break;
            case 3:
                this.signalIcon = R.drawable.ic_signal_wifi_3_bar_yellow_a700_48dp;
                break;
            case 4:
                this.signalIcon = R.drawable.ic_signal_wifi_4_bar_green_a700_48dp;
                break;
        }
    }


    public static Comparator<WifiDetail> COMPARE_BY_SIGNALSTRENGTH = new Comparator<WifiDetail>() {
        public int compare(WifiDetail one, WifiDetail two) {
            return two.signalLevel - one.signalLevel;
        }
    };
}
