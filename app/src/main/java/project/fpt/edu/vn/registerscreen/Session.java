package project.fpt.edu.vn.registerscreen;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 2/5/2018.
 */

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context ctx;
    String name;

    public String getName() {
        name = sharedPreferences.getString("user","");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("user", name).commit();
    }

    public Session(Context ctx){
        this.ctx = ctx;
        sharedPreferences = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean loggedIn){
        editor.putBoolean("LoggedInMode", loggedIn);
        editor.commit();
    }

    public boolean LoggedIn(){
        return sharedPreferences.getBoolean("LoggedInMode", false);
    }
}
