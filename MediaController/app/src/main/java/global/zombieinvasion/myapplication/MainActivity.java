package global.zombieinvasion.myapplication;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class MainActivity extends Activity implements MqttCallback {

    private MediaPlayer mediaPlayer;
    private TextView songText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MqttClient client = null;
        try
        {
            client = new MqttClient("tcp://192.168.20.114:1883", MqttClient.generateClientId(), null);

            client.setCallback(this);
        }
        catch (MqttException e1)
        {
            Log.d("mqtt", e1.getMessage());
        }


        MqttConnectOptions options = new MqttConnectOptions();
        try
        {
            client.connect(options);
        }
        catch (MqttException e)
        {
            Log.d(getClass().getCanonicalName(), "Connection attempt failed with reason code = " + e.getReasonCode() + ":" + e.getCause());
        }


        try
        {
            client.subscribe("audioPlayer");
        }
        catch (MqttException e)
        {
            Log.d(getClass().getCanonicalName(), "Subscribe failed with reason code = " + e.getReasonCode());
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.song);


    }

    public void connectionLost(Throwable cause) {
        Log.d(getClass().getCanonicalName(), "MQTT Server connection lost" + cause);
    }

    public void messageArrived(String topic, MqttMessage message) {


        Log.d(getClass().getCanonicalName(), "Message arrived:" + topic + ":" + message.toString());

        /**
         *
         *  http://localhost:8080/halloween/test/test?m={'action':'play','song':'song'}
         *  http://localhost:8080/halloween/test/test?m={'action':'stop'}
         */

        try {
            JSONObject jObject = new JSONObject(message.toString());

            String action = jObject.get("action").toString().trim();
            String song = jObject.get("song").toString().trim();

            Log.d("MyApp", "action:" + action + " | song:" + song);

            if (action.equals("play")) {

                if (song.equals("dog_jingle_bells")) {
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(this, R.raw.dog_jingle_bells);
                    mediaPlayer.start();

                }

                if (song.equals("christmas_is_coming")) {
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(this, R.raw.christmas_is_coming);
                    mediaPlayer.start();
                }

                if (song.equals("white_christmas")) {
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(this, R.raw.white_christmas);
                    mediaPlayer.start();
                }

                if (song.equals("jingle_beats")) {
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(this, R.raw.jingle_beats);
                    mediaPlayer.start();
                }

                if (song.equals("sugar_plum_fairy")) {
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(this, R.raw.sugar_plum_fairy);
                    mediaPlayer.start();
                }

            } else if (action.equals("stop")) {

                if (!mediaPlayer.equals(null))
                    mediaPlayer.stop();


            } else {
                Log.d("dunno", "wtf action:"+action);
            }

        } catch (Exception e) {
            Log.d(getClass().getCanonicalName(), e.getMessage());
        }
        
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.d(getClass().getCanonicalName(), "Delivery complete");
    }

}


