package project.fpt.edu.vn.registerscreen.Application;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import project.fpt.edu.vn.registerscreen.R;

/**
 * Created by GIang on 3/7/2018.
 */

public class SocketApplication extends Application {
    public static final boolean DEBUG = true;
    public static SocketApplication application;

    private static Context context;

    public static String    packageName;
    public static Resources resources;
    public static Socket CHAT_SOCKET;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //@formatter:off
        resources   = this.getResources();
        context     = getApplicationContext();
        packageName = getPackageName();
        //@formatter:on

        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = true;

        try {
            CHAT_SOCKET = IO.socket(getString(R.string.urlSocket), opts);
            Log.d("SOCKET.IO ","Socket connected");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.e("SOCKET.IO ", e.getMessage());
        }

    }

    public static Context getContext() {
        return context;
    }

    public static Socket getSocket() {
        return CHAT_SOCKET;
    }
}
