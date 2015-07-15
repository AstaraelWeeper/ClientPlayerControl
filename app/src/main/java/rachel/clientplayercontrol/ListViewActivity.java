package rachel.clientplayercontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class ListViewActivity extends ActionBarActivity implements MyClientTask.Listener{
    Context context;
    MyClientTask.Listener listener;
    Activity activity;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String IPaddress;
    Integer port;
    String[] messageBody;
    private ArrayAdapter<String> displayAdapter;
    private ArrayAdapter<String> displayAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        context = this;
        activity = this;
        listener = this;

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        IPaddress = sharedpreferences.getString((getString(R.string.pref_address_key)), getString(R.string.pref_address_default));
        port = Integer.parseInt(sharedpreferences.getString((getString(R.string.pref_port_key)), getString(R.string.pref_port_default)));

        Intent intent = getIntent();
        messageBody = intent.getStringArrayExtra("message");

        displayAdapter =
                new ArrayAdapter<String>(
                        activity, // The current context (this activity)
                        R.layout.list_item_layout, // The name of the layout ID.
                        R.id.listView, // The ID of the textview to populate.
                        messageBody);//can pass an empty array as on start will call the method now

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(displayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //putting in onclick item event
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //when clicking a thing in the list view, implement event here
                String selectedItem = displayAdapter.getItem(position);
                String message = "{\"messageType\":\"Sessions\",\"messageBody\":" + selectedItem + "}";
                MyClientTask clientTask = new MyClientTask(IPaddress, port,
                        message, listener, context);
                clientTask.execute();

            }
        });

        displayAdapter2 =
                new ArrayAdapter<String>(
                        activity, // The current context (this activity)
                        R.layout.list_item_layout, // The name of the layout ID.
                        R.id.listView2, // The ID of the textview to populate.
                        new ArrayList<String>());//can pass an empty array as on start will call the method now

        ListView listView2 = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(displayAdapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //putting in onclick item event
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //when clicking a thing in the list view, implement event here
                String selectedItem = displayAdapter2.getItem(position);
                String message = "{\"messageType\":\"SessionMedia\",\"messageBody\":" + selectedItem + "}";
                MyClientTask clientTask = new MyClientTask(IPaddress, port,
                        message, listener, context);
                clientTask.execute();

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

        return super.onOptionsItemSelected(item);
    }@Override
     public void onWifiMessageReturned(String response) {
        //handle message here

        if(response.contains("CONNECTION_ACTIVE_WIFI")) {

        }

    }


}
