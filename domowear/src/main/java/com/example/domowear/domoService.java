package com.example.domowear;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
/**
 *Servicio encargado del control de sensores y receviers,
 *  asi como de cargar y procesar los datos de los contactos
 */
public class domoService extends Service implements IAccelerometerListener{



    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (AccelerometerManager.isSupported(this)) {
            AccelerometerManager.startListening(this, 29);
        } return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        //Desactivo sensores y listeners

        if (AccelerometerManager.isListening()) {
            AccelerometerManager.stopListening();
            Toast.makeText(getBaseContext(),"Acelerometro desactivado", Toast.LENGTH_SHORT).show();
        }
        Log.i("xtian", "service onDestroy");
    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onDoubleShake(float force) {
    }
    @Override
    public void onShake(float force) {
        Intent startIntent = new Intent(getBaseContext(), MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startIntent);
    }
}
