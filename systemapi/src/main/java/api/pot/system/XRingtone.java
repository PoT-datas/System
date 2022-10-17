package api.pot.system;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

public class XRingtone {
    public static void play(Context context){
        /*Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();*/

        //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mp = MediaPlayer.create(context.getApplicationContext(), notification);
        mp.start();
    }
}