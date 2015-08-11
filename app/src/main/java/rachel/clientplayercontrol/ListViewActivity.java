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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class ListViewActivity extends ActionBarActivity implements MyClientTask.Listener{
    private final String LOG_TAG = ListViewActivity.class.getSimpleName();
    TextView textView, textView2;
    Context context;
    MyClientTask.Listener listener;
    Activity activity;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String IPaddress;
    Integer port;
    String messageBody;
    String messageType;
    ArrayList<String> sessionsArray;
    ArrayList<String> locationsArray;
    ArrayList<String> MediaArray;
    ArrayList<String> placeholder;
    private ArrayAdapter<String> displayAdapter;
    private ArrayAdapter<String> displayAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        context = this;
        activity = this;
        listener = this;
        textView = (TextView) findViewById(R.id.textView1);
        textView.setText(getString(R.string.label_sessions));//should be initialised to "sessions"
        textView2 = (TextView) findViewById(R.id.textView2);//should be blank

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        IPaddress = sharedpreferences.getString((getString(R.string.pref_address_key)), getString(R.string.pref_address_default));
        port = Integer.parseInt(sharedpreferences.getString((getString(R.string.pref_port_key)), getString(R.string.pref_port_default)));

        Intent intent = getIntent();
        String [] sessionsStringArray = intent.getStringArrayExtra("messageBody");
        sessionsArray = new ArrayList<String>(Arrays.asList(sessionsStringArray));
        Log.v(LOG_TAG, "Array result: " + sessionsArray);


        displayAdapter =
                new ArrayAdapter<String>(
                        activity, // The current context (this activity)
                        R.layout.list_item_layout, // The name of the layout ID.
                        R.id.list_item_textview, // The ID of the textview to populate.
                        placeholder = new ArrayList<>());//can pass an empty array as on start will call the method now

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(displayAdapter);
        displayAdapter.addAll(sessionsArray);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //putting in onclick item event
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //when clicking a thing in the list view, implement event here
               // String selectedItem = displayAdapter.getItem(position);
                //sending the number position rather than the string at the position.
                String positionString = Integer.toString(position);
                String message = "{\"messageType\":\"Sessions\",\"messageBody\":\"" + positionString + "\"}";
                MyClientTask clientTask = new MyClientTask(IPaddress, port,
                        message, listener, context);
                clientTask.execute();

            }
        });

        displayAdapter2 =
                new ArrayAdapter<String>(
                        activity, // The current context (this activity)
                        R.layout.list_item_layout, // The name of the layout ID.
                        R.id.list_item_textview, // The ID of the textview to populate.
                        placeholder = new ArrayList<>());//can pass an empty array as on start will call the method now

        ListView listView2 = (ListView) findViewById(R.id.listView2);
        listView2.setAdapter(displayAdapter2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //putting in onclick item event
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //when clicking a thing in the list view, implement event here
                String positionString = Integer.toString(position);
                String selectedItem = displayAdapter2.getItem(position);
                if(selectedItem != "") {
                    String message = "{\"messageType\":\"Media\",\"messageBody\":\"" + positionString + "\"}";
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
        getMenuInflater().inflate(R.menu.menu_list_view, menu);
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
        if(id==R.id.action_backToSessions){
            BackToSessions(sessionsArray);
        }

        return super.onOptionsItemSelected(item);
    }@Override
     public void onWifiMessageReturned(String response) {
        //handle message here

        if(response.contains("CONNECTION_ACTIVE_WIFI")) {
        }
        Log.v(LOG_TAG, "passing to JSONParsing");
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


        if (messageType.equals("Detail")) {
            Log.v(LOG_TAG,"passing to array builder");
                messageBodyToArray();
        }
        else {
            if (messageType.equals("Media")) {
                Toast.makeText(context, messageBody, Toast.LENGTH_LONG).show();
            }
        }
    };

    void messageBodyToArray(){
        String node_array_locations = "locations";
        String node_array_media = "media";
        String node_name = "name";
        String node_ELR = "ELR";
        String node_Line = "Line";
        String node_Distance = "Distance";
        String node_Date = "Date";
        String node_type = "type";


        try {
            Log.v(LOG_TAG, "in try block");
            JSONObject jsonbody = new JSONObject(messageBody);
            JSONArray jsonitemslocation = jsonbody.getJSONArray(node_array_locations);
            JSONArray jsonitemsmedia = jsonbody.getJSONArray(node_array_media);
            String name;
            String ELR;
            String line;
            String distance;
            String date;
            String type;
            String [] locationsArrayString = new String[jsonitemslocation.length()];
            String [] MediaArrayString = new String[jsonitemsmedia.length()];

            for(int i =0; i< jsonitemslocation.length();i++)
            {
                JSONObject item = jsonitemslocation.getJSONObject(i);
                name = item.getString(node_name);
                ELR = item.getString(node_ELR);
                line = item.getString(node_Line);
                distance = item.getString(node_Distance);
                date = item.getString(node_Date);
                locationsArrayString[i] = "Name: " + name + ", ELR: " + ELR + ", Line: " + line + ", Distance: " + distance + ", Date: " + date;
            }
            for(int i =0; i< jsonitemsmedia.length();i++)
            {
                JSONObject item = jsonitemsmedia.getJSONObject(i);
                name = item.getString(node_name);
                type = item.getString(node_type);
                MediaArrayString[i] = "Name: " + name + ", Date: " + type;
            }
            locationsArray = new ArrayList<String>(Arrays.asList(locationsArrayString));
            MediaArray = new ArrayList<String>(Arrays.asList(MediaArrayString));
            textView.setText(getString(R.string.label_locations));//update titles to reflect new list content
            textView2.setText(getString(R.string.label_media));
            displayAdapter.clear();
            displayAdapter.addAll(locationsArray);
            displayAdapter2.clear();
            displayAdapter2.addAll(MediaArray);



        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void BackToSessions(ArrayList<String> sessionsArray) {
        textView.setText(getString(R.string.label_sessions));
        textView2.setText("");
        displayAdapter.clear();
        displayAdapter2.clear();
        displayAdapter.addAll(sessionsArray);
    }


}
