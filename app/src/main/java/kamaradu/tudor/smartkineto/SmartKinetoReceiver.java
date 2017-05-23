package kamaradu.tudor.smartkineto;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.backendless.push.BackendlessBroadcastReceiver;

/**
 * Created by Tudor on 18.05.2017.
 */

public class SmartKinetoReceiver extends BackendlessBroadcastReceiver {
    @Override
    public boolean onMessage(Context context, Intent intent) {
        //ToDo Replace with no depraceted method, csf n-ai csf
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.sirene);
        mediaPlayer.start();

        return true;
    }
}

