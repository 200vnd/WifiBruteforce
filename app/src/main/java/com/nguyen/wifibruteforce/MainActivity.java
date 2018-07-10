package com.nguyen.wifibruteforce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyen.wifibruteforce.adapter.WifiListAdapter;
import com.nguyen.wifibruteforce.model.WifiDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lvNetworkList)
    ListView lvNetworkList;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;

    @BindView(R.id.txtNameDetail)
    TextView txtNameDetail;
    @BindView(R.id.txtSecurity)
    TextView txtSecurity;
    @BindView(R.id.txtIP)
    TextView txtIP;
    @BindView(R.id.txtSignalDetail)
    TextView txtSignalDetail;
    @BindView(R.id.imgSignalDetail)
    ImageView imgSignalDetail;
    @BindView(R.id.constraint)
    ConstraintLayout constraint;

    ArrayList<WifiDetail> listNetwork = new ArrayList<>();
    WifiListAdapter adapter;

    WifiManager wifi;

    WifiDetail wifiDetail;
    WifiScanReceiver wifiScanReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //TODO dua listview vao asynctask: hien progress bar den khi listview load xong, constraint visible cung luc listview load xong
        //bo thoi gian cho pulltorefresh
        wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        wifiScanReceiver = new WifiScanReceiver();
        constraint.setVisibility(View.VISIBLE);

        updateListNetworks();
        myPullToRefresh();

    }

    private void updateListNetworks() {


        Utils utils = new Utils();
        utils.checkWifiConnect(this, this);
        utils.displayLocationSettingsRequest(this, this);
        boolean isConnectAP = utils.isConnectAccessPoint(this);
        String ip = utils.getWifiIpAddress(this);

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //TODO startScan() run right after turn on gps or wifi
        if (wifi.getWifiState() == WifiManager.WIFI_STATE_ENABLED && statusOfGPS) {
            wifi.startScan();
            updateCurrentWifi(isConnectAP, ip);
//            updateCurrentWifi(scanResults);

        }

//        //random data for testing listview
//        for (int j = 0; j < 20; j++) {
//            Random random = new Random();
//            int x = random.nextInt(4) + 1;
//            WifiDetail n = new WifiDetail();
//            n.setName("wifi" + j);
//            n.setSignalLevel(x);
////            n.setSignalIcon();
//            listNetwork.add(n);
//        }
//
//        for (int i = 0; i < listNetwork.size(); i++) {
//            listNetwork.get(i).setSignalIcon();
//        }

//        Collections.sort(listNetwork, WifiDetail.COMPARE_BY_SIGNALSTRENGTH);
//        adapter.notifyDataSetChanged();
    }

    private void scanWifi(List<ScanResult> scanResults) {
        listNetwork.clear();
        adapter = new WifiListAdapter(this, listNetwork);
        lvNetworkList.setAdapter(adapter);

        for (ScanResult result : scanResults) {
            wifiDetail = new WifiDetail();
            wifiDetail.setSignalLevel(result);
            wifiDetail.setRSSI(result.level);
            wifiDetail.setName(result.SSID);
            wifiDetail.setBSSID(result.BSSID);
            wifiDetail.setSignalIcon();
//            wifiDetail.setCapabilities(result.capabilities);
//            Log.d("scancap", wifiDetail.getName() + "-" + wifiDetail.getCapabilities());
            listNetwork.add(wifiDetail);
        }
        Collections.sort(listNetwork, WifiDetail.COMPARE_BY_SIGNALSTRENGTH);
        adapter.notifyDataSetChanged();


    }

    public void updateCurrentWifi( boolean isConnectAP, String ip) {
//        Utils utils2 = new Utils();
//        boolean isConnectAP = utils2.isConnectAccessPoint(this);
//        String ip = utils2.getWifiIpAddress(this);
        List<ScanResult> scanResults = wifi.getScanResults();
        if (isConnectAP) {
            constraint.setVisibility(View.VISIBLE); //show view of current connected wifi info

            WifiInfo wifiInfo = wifi.getConnectionInfo();
            WifiDetail wifiCurrentDetail = new WifiDetail();
            wifiCurrentDetail.setName(wifiInfo.getSSID());
            wifiCurrentDetail.setBSSID(wifiInfo.getBSSID());
            wifiCurrentDetail.setRSSI(wifiInfo.getRssi());
            wifiCurrentDetail.setSignalLevel(wifiInfo.getRssi());
            wifiCurrentDetail.setIP(ip);



            wifiCurrentDetail.setSignalIcon();
            if (scanResults != null) {
                for (ScanResult network : scanResults) {
                    //check if current connected SSID
                    if (Utils.convertSSID(wifiInfo.getSSID()).equals(network.SSID)) {
                        //get capabilities of current connection
                        String capabilities = network.capabilities;
//                        Log.d("capab", capabilities);

                        if ((capabilities.contains("WPA2") ||
                                capabilities.contains("WPA") ||
                                capabilities.contains("WEP"))) {
                            wifiCurrentDetail.setCapabilities(capabilities);
                            txtSecurity.setText(wifiCurrentDetail.getCapabilities());
                            txtSecurity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_grey_500_18dp,0,0,0);
                        } else {
                            wifiCurrentDetail.setCapabilities("Open");
                            txtSecurity.setText(wifiCurrentDetail.getCapabilities());
                            txtSecurity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_open_grey_500_18dp,0,0,0);
                        }
                    }
                }
            }

            txtNameDetail.setText(wifiCurrentDetail.getName() + "\n"+ "(" + wifiCurrentDetail.getBSSID() + ")");
            txtIP.setText(wifiCurrentDetail.getIP());
            txtSignalDetail.setText(""+wifiCurrentDetail.getRSSI());
            imgSignalDetail.setImageResource(wifiCurrentDetail.getSignalIcon());

        }else
            constraint.setVisibility(View.GONE); //remove/hide view of current connected wifi info


    }

    private void myPullToRefresh() {
        //workaround listview scrolling issue with pulltorefresh
        lvNetworkList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    pullToRefresh.setEnabled(true);
                } else
                    pullToRefresh.setEnabled(false);
            }
        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateListNetworks();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Stop animation (This will be after 3 seconds)
                        pullToRefresh.setRefreshing(false);
                    }
                }, 3000); // Delay in millis
            }
        });
        pullToRefresh.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_red_light),
                getResources().getColor(android.R.color.holo_blue_light),
                getResources().getColor(android.R.color.holo_purple)
        );
    }

    //click a wifi in listview
    @OnItemClick(R.id.lvNetworkList)
    void onItemClick(int position) {
        Toast.makeText(this, "You clicked: " + adapter.getItem(position), Toast.LENGTH_LONG).show();
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Toast.makeText(getApplicationContext(), "yes ", Toast.LENGTH_LONG).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Start brute-force this wifi?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @OnItemLongClick(R.id.lvNetworkList)
    boolean onItemLongClick(int position) {
        Toast.makeText(getApplicationContext(), "Long click " + position, Toast.LENGTH_LONG).show();
        return true; //if return false, the onItemClick() will be invoked when touch up
    }

    @Override
    protected void onPause() {
        unregisterReceiver(wifiScanReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(wifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    private class WifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
//            Log.d("resultsSize", "" + results.size());
            List<ScanResult> results = wifi.getScanResults();
            scanWifi(results);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(this, SettingActivity.class);
                this.startActivity(intent);
                break;
            case R.id.refresh:
                updateListNetworks();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
