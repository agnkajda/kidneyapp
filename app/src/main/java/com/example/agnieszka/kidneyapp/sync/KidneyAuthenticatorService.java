package com.example.agnieszka.kidneyapp.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * The service which allows the sync adapter framework to access the authenticator.
 */
public class KidneyAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private KidneyAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new KidneyAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}