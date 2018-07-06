package com.nguyen.wifibruteforce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyen.wifibruteforce.adapter.NetworkListAdapter;
import com.nguyen.wifibruteforce.model.WifiDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

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
    @BindView(R.id.txtMAC)
    TextView txtMAC;
    @BindView(R.id.txtSignalDetail)
    TextView txtSignalDetail;
    @BindView(R.id.imgSignalDetail)
    ImageView imgSignalDetail;

    ArrayList<WifiDetail> listNetwork = new ArrayList<>();
    NetworkListAdapter adapter;

    WifiManager wifi;
    List<ScanResult> results;
    WifiDetail wifiDetail;
    WifiScanReceiver wifiScanReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiScanReceiver = new WifiScanReceiver();

        updateListNetworks();
        myPullToRefresh();

    }

    private void updateListNetworks() {
        listNetwork.clear();
        adapter = new NetworkListAdapter(this, listNetwork);
        lvNetworkList.setAdapter(adapter);

        Ultils ultils = new Ultils();
        ultils.checkWifiConnect(this);
        ultils.displayLocationSettingsRequest(this,this);

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //TODO startScan() run right after turn on gps or wifi
        if(wifi.getWifiState() == WifiManager.WIFI_STATE_ENABLED && statusOfGPS) {
            wifi.startScan();
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

        int lvl = 5;
        for (ScanResult result: scanResults) {
            wifiDetail = new WifiDetail();
            int calculatedLvl = WifiManager.calculateSignalLevel(result.level, lvl);
            wifiDetail.setSignalLevel(calculatedLvl);
            wifiDetail.setRSSI(result.level);
            wifiDetail.setName(result.SSID);
            wifiDetail.setBSSID(result.BSSID);
            wifiDetail.setSignalIcon();

            listNetwork.add(wifiDetail);
        }
        Collections.sort(listNetwork, WifiDetail.COMPARE_BY_SIGNALSTRENGTH);
        adapter.notifyDataSetChanged();

        //        //get current connected SSID for comparison to ScanResult
//        WifiInfo wi = wifi.getConnectionInfo();
//        String currentSSID = wi.getSSID();
//
//        if (networkList != null) {
//            for (ScanResult network : networkList) {
//                //check if current connected SSID
//                if (currentSSID.equals(network.SSID)) {
//                    //get capabilities of current connection
//                    String capabilities =  network.capabilities;
//
//                    if (!(capabilities.contains("WPA2") &&
//                            capabilities.contains("WPA") &&
//                            capabilities.contains("WEP"))) {
//                        wifiDetail.setCapabilities("Open Wifi");
//                    }
//                }
//            }
//        }
    }

    public void currentWifi() {

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
                }else
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
                }, 1000); // Delay in millis
            }
        });
        pullToRefresh.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_red_light)
        );
    }

    //click a wifi in listview
    @OnItemClick(R.id.lvNetworkList)
    void onItemClick(int position) {
        Toast.makeText(this, "You clicked: " + adapter.getItem(position), Toast.LENGTH_LONG).show();
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
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
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
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
            results = wifi.getScanResults();
            Log.d("resultsSize", "" + results.size());
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
