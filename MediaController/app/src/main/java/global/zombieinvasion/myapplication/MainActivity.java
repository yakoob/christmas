package global.zombieinvasion.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;



import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import android.media.MediaPlayer.OnTimedTextListener;
import android.media.MediaPlayer.TrackInfo;
import android.media.TimedText;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends Activity implements MqttCallback, OnTimedTextListener{

    private MediaPlayer mediaPlayer;
    private TextView songText;

    private static final String TAG = "TimedTextTest";

    private static Handler handler = new Handler();

    private static MqttClient client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

    public void setMediaTextCallBack(){

        final MainActivity hello = this;

        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {
            public void run() {

                try {
                    mediaPlayer.addTimedTextSource(getSubtitleFile(R.raw.sub), MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
                    int textTrackIndex = findTrackIndexFor(TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, mediaPlayer.getTrackInfo());
                    if (textTrackIndex >= 0) {
                        mediaPlayer.selectTrack(textTrackIndex);
                    } else {
                        Log.d("fuck", "Cannot find text track!");
                    }
                    mediaPlayer.setOnTimedTextListener(hello);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


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
                    setMediaTextCallBack();
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




    private int findTrackIndexFor(int mediaTrackType, TrackInfo[] trackInfo) {
        int index = -1;
        for (int i = 0; i < trackInfo.length; i++) {
            if (trackInfo[i].getTrackType() == mediaTrackType) {
                return i;
            }
        }
        return index;
    }

    private String getSubtitleFile(int resId) {
        String fileName = getResources().getResourceEntryName(resId);
        File subtitleFile = getFileStreamPath(fileName);
        if (subtitleFile.exists()) {
            Log.d(TAG, "Subtitle already exists");
            return subtitleFile.getAbsolutePath();
        }
        Log.d(TAG, "Subtitle does not exists, copy it from res/raw");

        // Copy the file from the res/raw folder to your app folder on the
        // device
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = getResources().openRawResource(resId);
            outputStream = new FileOutputStream(subtitleFile, false);
            copyFile(inputStream, outputStream);
            return subtitleFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStreams(inputStream, outputStream);
        }
        return "";
    }

    private void copyFile(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        final int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        int length = -1;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
    }

    // A handy method I use to close all the streams
    private void closeStreams(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable stream : closeables) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onTimedText(final MediaPlayer mp, final TimedText text) {
        if (text != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    MqttMessage message = new MqttMessage();
                    message.setPayload(text.getText().getBytes());
                    try {
                        client.publish("/FOOOOOOBAR", message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    Log.d("yasss", text.getText());

                }
            });
        }
    }

    // To display the seconds in the duration format 00:00:00
    public String secondsToDuration(int seconds) {
        return String.format("%02d:%02d:%02d", seconds / 3600,
                (seconds % 3600) / 60, (seconds % 60), Locale.US);
    }



}


