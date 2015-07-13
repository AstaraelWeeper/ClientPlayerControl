package rachel.clientplayercontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements MyClientTask.Listener{

    TextView textResponse, textLogs;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonRepairWifi, buttonClear;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    Context context;
    MyClientTask.Listener listener;
    Activity activity;
    WifiManager wifiManager;

    EditText welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        context = this;
        activity = this;
        listener = this;
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);



        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonClear = (Button) findViewById(R.id.clear);
        buttonRepairWifi = (Button) findViewById(R.id.btn_repair);
        textResponse = (TextView) findViewById(R.id.response);
        textLogs = (TextView) findViewById(R.id.textView2);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        buttonRepairWifi.setOnClickListener(buttonRepairWifiOnClickListener);

        buttonClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                textResponse.setText("");
            }
        });

    }

    OnClickListener buttonRepairWifiOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            wifiManager.setWifiEnabled(false);
            wifiManager.setWifiEnabled(true);
            boolean wifiEnabled = wifiManager.isWifiEnabled();

            if(wifiEnabled)
            {
                textLogs.setText("WiFi reconnected");
            }
            else {
                textLogs.setText("WiFi repair failed");
            }




        }
    };

    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            String connection = "CONNECTION_ACTIVE_WIFI";
            String address = editTextAddress
                    .getText().toString();
            String port = editTextPort
                    .getText().toString();

            textLogs.setText(address);

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString(getString(R.string.pref_address_key), address);
            editor.putString(getString(R.string.pref_port_key), port);
            editor.commit();

            MyClientTask myClientTask = new MyClientTask(address,Integer.parseInt(port), //need to save these in shared preferences for access
                    connection,listener, context);
            myClientTask.execute();


        }
    };

    @Override
    public void onWifiMessageReturned(String string, String address) {
          //handle message here
        textResponse.setText(string);
        textLogs.setText(address);
        if(string.contains("CONNECTION_ACTIVE_WIFI")) {
            Intent intent = new Intent(context, PlayerControlActivity.class);
            startActivity(intent);
        }
    }
}
