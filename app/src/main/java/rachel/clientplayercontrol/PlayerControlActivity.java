package rachel.clientplayercontrol;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;


public class PlayerControlActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_control);

        Button btn_play = (Button) findViewById(R.id.btn_Play);
        Button btn_pause = (Button) findViewById(R.id.btn_pause);
        Button btn_stop = (Button) findViewById(R.id.btn_stop);
        Button btn_step_backwards = (Button) findViewById(R.id.btn_step_backwards);
        Button btn_step_forwards = (Button) findViewById(R.id.btn_step_forwards);
        final RadioButton btn_play_backwards = (RadioButton)findViewById(R.id.btn_play_backwards);
        final RadioButton btn_play_forwards = (RadioButton)findViewById(R.id.btn_play_forwards);

        btn_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "play";
                MyClientTask clientTask = new MyClientTask(//textAddress (SP), port (SP),
                        message);
            }
        });
        btn_pause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "pause";
                MyClientTask clientTask = new MyClientTask(//textAddress (SP), port (SP),
                        message);
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "stop";
                MyClientTask clientTask = new MyClientTask(//textAddress (SP), port (SP),
                        message);
            }
        });
        btn_step_backwards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "step back";
                MyClientTask clientTask = new MyClientTask(//textAddress (SP), port (SP),
                        message);
            }
        });
        btn_step_forwards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String message = "step forwards";
                MyClientTask clientTask = new MyClientTask(//textAddress (SP), port (SP),
                        message);
            }
        });
        btn_play_backwards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(btn_play_backwards.isChecked()) {
                    String message = "play backwards";
                    MyClientTask clientTask = new MyClientTask(//textAddress (SP), port (SP),
                            message);
                }
            }
        });
        btn_play_forwards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(btn_play_forwards.isChecked()) {
                    String message = "play forwards";
                    MyClientTask clientTask = new MyClientTask(//textAddress (SP), port (SP),
                            message);
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
}
