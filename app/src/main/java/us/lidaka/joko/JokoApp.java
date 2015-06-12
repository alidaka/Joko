package us.lidaka.joko;

import android.app.Application;
import android.content.Context;

/**
 * Created by augustus on 6/8/15.
 */
public class JokoApp extends Application {

    private static JokoApp instance;

    public JokoApp() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

}
