package rachel.clientplayercontrol;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Rachel on 08/07/2015.
 */
public class MyClientTask extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    int dstPort;
    String response;
    String msgToServer;
    Listener listener;
    Context context;


    MyClientTask(String addr, int port, String msgTo, Listener listenerin, Context contextIn) {
        dstAddress = addr;
        dstPort = port;
        msgToServer = msgTo;
        listener = listenerin;
        context = contextIn;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(dstAddress, dstPort), 2500);
            dataOutputStream = new DataOutputStream(
                    socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            if(msgToServer != null){
                dataOutputStream.writeUTF(msgToServer);
            }

            InputStream inputStream = socket.getInputStream();

            InputStreamReader is = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(is);

            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                response += currentLine;
            }


        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //textResponse.setText(response);

        response = response.replace("null", "");

        listener.onWifiMessageReturned(response);
        super.onPostExecute(result);
    }

    public interface Listener
    {
        void onWifiMessageReturned(String string);
    }



}