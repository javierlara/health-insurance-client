package com.apres.apresmovil;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.apres.apresmovil.network.NoSSLv3Factory;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by javierlara on 11/12/16.
 */
public class HealthInsuranceApplication extends Application {
    private RequestQueue mRequestQueue;
    private static HealthInsuranceApplication mInstance;
    public static final String TAG = HealthInsuranceApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        HttpsURLConnection.setDefaultSSLSocketFactory(new NoSSLv3Factory());
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static synchronized HealthInsuranceApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancel() {
        mRequestQueue.cancelAll(TAG);
    }
}
