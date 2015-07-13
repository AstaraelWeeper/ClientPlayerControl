package rachel.clientplayercontrol;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements MyClientTask.Listener{

    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    Context context;
    MyClientTask.Listener listener;
    Activity activity;

    EditText welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        context = this;
        activity = this;



        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonClear = (Button) findViewById(R.id.clear);
        textResponse = (TextView) findViewById(R.id.response);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

        buttonClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                textResponse.setText("");
            }
        });

    }

    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            String connection = "Connection_Active";
            String address = editTextAddress
                    .getText().toString();
            String port = editTextPort
                    .getText().toString();

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString(getString(R.string.pref_address_key), address);
            editor.putString(getString(R.string.pref_port_key), port);
            editor.commit();

            MyClientTask myClientTask = new MyClientTask(address,Integer.parseInt(port), //need to save these in shared preferences for access
                    connection,listener, context);
            myClientTask.execute();

            //Intent intent = new Intent(context, PlayerControlActivity.class);
            //startActivity(intent); //not resolving?? //should this go here, or in the post execute? needs to only happen for the connect message though...
        }
    };

    @Override
    public void onWifiMessageReturned(String string) {
          //handle message here
    }
}
