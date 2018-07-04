package com.nguyen.wifibruteforce;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyen.wifibruteforce.adapter.NetworkListAdapter;
import com.nguyen.wifibruteforce.model.WifiDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lvNetworkList)
    ListView lvNetworkList;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;

    ArrayList<WifiDetail> listNetwork = new ArrayList<WifiDetail>();
    NetworkListAdapter adapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        myPullToRefresh();


        adapter = new NetworkListAdapter(this, listNetwork);
        lvNetworkList.setAdapter(adapter);
        updateListNetworks();
    }

    private void updateListNetworks() {
        Ultils ultils = new Ultils();
        ultils.checkConnect(this);

        listNetwork.clear();

        //random data for testing
        for (int j = 0; j < 20; j++) {
            Random random = new Random();
            int x = random.nextInt(4) + 1;
            WifiDetail n = new WifiDetail("wifi" + j, x);
//            n.setSignalIcon();
            listNetwork.add(n);
        }

        for (int i = 0; i < listNetwork.size(); i++) {
            listNetwork.get(i).setSignalIcon();
        }
        Collections.sort(listNetwork, WifiDetail.COMPARE_BY_SIGNALSTRENGTH);
        adapter.notifyDataSetChanged();
    }

    private void myPullToRefresh() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateListNetworks();
                //                pullToRefresh.setRefreshing(false);
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
