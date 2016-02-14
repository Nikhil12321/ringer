package com.example.nikhil.ringer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReciever extends BroadcastReceiver {
    public SMSReciever() {
    }
    final SmsManager sms = SmsManager.getDefault();
    String ringerString = "ring";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        final Bundle bundle = intent.getExtras();
        AudioManager audio = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        //Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();
        try{
            if(bundle!=null){
                final Object[] pdusObj = (Object[])bundle.get("pdus");
                for (int i=0; i<pdusObj.length; i++){
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[])pdusObj[i]);
                    String message = currentMessage.getDisplayMessageBody();
                    /*
                        SET ringer volume to full
                     */
                    if(message.contains(ringerString)){
                        int s = audio.getRingerMode();
                        audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        audio.setStreamVolume(AudioManager.STREAM_RING,
                                audio.getStreamMaxVolume(AudioManager.STREAM_RING),
                                AudioManager.AUDIOFOCUS_GAIN);

                    }
                }
            }
        }
        catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        /*
            An unwanted comment to commit changes

         */
    }
}
