package rachel.clientplayercontrol;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PlayerControlActivity extends ActionBarActivity implements MyClientTask.Listener{
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
        Button btn_pause = (Button) findViewById(R.id.btn_pause);
        Button btn_stop = (Button) findViewById(R.id.btn_stop);
        Button btn_step_backwards = (Button) findViewById(R.id.btn_step_backwards);
        Button btn_step_forwards = (Button) findViewById(R.id.btn_step_forwards);
        final RadioButton btn_play_backwards = (RadioButton)findViewById(R.id.btn_play_backwards);
        final RadioButton btn_play_forwards = (RadioButton)findViewById(R.id.btn_play_forwards);
        IPaddress = sharedpreferences.getString((getString(R.string.pref_address_key)),getString(R.string.pref_address_default));
        port = Integer.parseInt(sharedpreferences.getString((getString(R.string.pref_port_key)), getString(R.string.pref_port_default)));

        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        displayAdapter =
                new ArrayAdapter<String>(
                        activity, // The current context (this activity)
                        R.layout.list_item_layout, // The name of the layout ID.
                        R.id.list_item_textview, // The ID of the textview to populate.
                        new ArrayList<String>());//can pass an empty array as on start will call the method now

        ListView listView = (ListView) findViewById(R.id.listView_Display);
        listView.setAdapter(displayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            //putting in onclick item event
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int position, long l) {

               //when clicking a thing in the list view, implement event here

            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "{\"messageType\":\"VideoPlayer\",\"messageBody\":\"Play\"}";
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
        if(!response.contains("CONNECTION_ACTIVE_WIFI")) {
            JSONParsing(response);
            useMessage();
        }

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

    };

    void useMessage()
    {
        if(messageType == "VideoPlayer")
        {
            if(messageBody == "Playing")
            {

            }


        }


    }
}
