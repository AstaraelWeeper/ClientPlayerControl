package rachel.clientplayercontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PlayerControlActivity extends ActionBarActivity implements MyClientTask.Listener{
    private final String LOG_TAG = PlayerControlActivity.class.getSimpleName();
    Context context;
    MyClientTask.Listener listener;
    Activity activity;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String IPaddress;
    Integer port;
    TextView responseView;
    String messageType;
    String messageBody;
    private ArrayAdapter<String> displayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_control);
        context = this;
        activity = this;
        listener = this;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        responseView = (TextView)findViewById(R.id.txtResponse);
        Button btn_play = (Button) findViewById(R.id.btn_Play);
        Button btn_load = (Button) findViewById(R.id.btn_Load);
        Button btn_pause = (Button) findViewById(R.id.btn_pause);
        Button btn_stop = (Button) findViewById(R.id.btn_stop);
        Button btn_step_backwards = (Button) findViewById(R.id.btn_step_backwards);
        Button btn_step_forwards = (Button) findViewById(R.id.btn_step_forwards);
        final RadioButton btn_play_backwards = (RadioButton)findViewById(R.id.btn_play_backwards);
        final RadioButton btn_play_forwards = (RadioButton)findViewById(R.id.btn_play_forwards);
        IPaddress = sharedpreferences.getString((getString(R.string.pref_address_key)),getString(R.string.pref_address_default));
        port = Integer.parseInt(sharedpreferences.getString((getString(R.string.pref_port_key)), getString(R.string.pref_port_default)));



        btn_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "{\"messageType\":\"VideoPlayer\",\"messageBody\":\"Play\"}";
                MyClientTask clientTask = new MyClientTask(IPaddress, port,
                        message, listener, context);
                clientTask.execute();
            }
        });
        btn_load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "{\"messageType\":\"Sessions\",\"messageBody\":\"Load\"}";
                MyClientTask clientTask = new MyClientTask(IPaddress, port,
                        message, listener, context);
                clientTask.execute();
            }
        });
        btn_pause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "{\"messageType\":\"VideoPlayer\",\"messageBody\":\"Pause\"}";
                MyClientTask clientTask = new MyClientTask(IPaddress, port,
                        message, listener, context);
                clientTask.execute();
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "{\"messageType\":\"VideoPlayer\",\"messageBody\":\"Stop\"}";
                MyClientTask clientTask = new MyClientTask(IPaddress, port,
                        message, listener, context);
                clientTask.execute();
            }
        });
        btn_step_backwards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "{\"messageType\":\"VideoPlayer\",\"messageBody\":\"Step Back\"}";
                MyClientTask clientTask = new MyClientTask(IPaddress, port,
                        message, listener, context);
                clientTask.execute();
            }
        });
        btn_step_forwards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "{\"messageType\":\"VideoPlayer\",\"messageBody\":\"Step Forwards\"}";
                MyClientTask clientTask = new MyClientTask(IPaddress, port,
                        message, listener, context);
                clientTask.execute();
            }
        });
        btn_play_backwards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(btn_play_backwards.isChecked()) {
                    String message = "{\"messageType\":\"VideoPlayer\",\"messageBody\":\"Play Backwards\"}";
                    MyClientTask clientTask = new MyClientTask(IPaddress, port,
                            message, listener, context);
                    clientTask.execute();
                }
            }
        });
        btn_play_forwards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(btn_play_forwards.isChecked()) {
                    String message = "{\"messageType\":\"VideoPlayer\",\"messageBody\":\"Play Forwards\"}";
                    MyClientTask clientTask = new MyClientTask(IPaddress, port,
                            message, listener, context);
                    clientTask.execute();
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWifiMessageReturned(String response) {
        //handle message here
        responseView.setText(response);
        if(response.contains("CONNECTION_ACTIVE_WIFI")) {

        }

        JSONParsing(response);
    }
    void JSONParsing(String JSON)
    {
        String node_messageType = "messageType";
        String node_messageBody = "messageBody";

        try {
            JSONObject jsonobj = new JSONObject(JSON);
            messageType = jsonobj.getString(node_messageType);
            messageBody = jsonobj.getString(node_messageBody);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        if(messageType.equals("VideoPlayer"))
        {
            if(messageBody.equals("Playing"))
            {

            }


        }
        else {
            if (messageType.equals("Sessions")) {
                String[] message = messageBodyToArray();
                Log.v(LOG_TAG, "Array result: " + message);
                Intent intent = new Intent(context, ListViewActivity.class);
                intent.putExtra("messageBody", message);
                startActivity(intent);

            }
        }

    };

    String[] messageBodyToArray(){
        String[] listArray = new String[0];


            String node_array_sessions = "sessions";
            String node_name = "name";
            String node_date = "date";
            String node_list = "list";

            try {
                JSONObject jsonbody = new JSONObject(messageBody);
                JSONArray jsonitems = jsonbody.getJSONArray(node_array_sessions);
                String name;
                String date;
                String list;
                listArray = new String[jsonitems.length()];

                for(int i =0; i< jsonitems.length();i++)
                {
                    JSONObject item = jsonitems.getJSONObject(i);
                    name = item.getString(node_name);
                    date = item.getString(node_date);
                    list = item.getString(node_list);

                    listArray[i] = "Name: " + name + ", Date: " + date + ", List: " + list;

                }


            }catch (JSONException e) {
                e.printStackTrace();
            }
            return listArray;
    }
}
