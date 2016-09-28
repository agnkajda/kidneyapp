package com.example.agnieszka.kidneyapp.sync;

        import android.app.Service;
        import android.content.Intent;
        import android.os.IBinder;
        import android.util.Log;

public class KidneySyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static KidneySyncAdapter sKidneySyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("KidneySyncService", "onCreate - KidneySyncService");
        synchronized (sSyncAdapterLock) {
            if (sKidneySyncAdapter == null) {
                sKidneySyncAdapter = new KidneySyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sKidneySyncAdapter.getSyncAdapterBinder();
    }
}