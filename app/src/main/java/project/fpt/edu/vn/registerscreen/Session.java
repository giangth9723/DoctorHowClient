package project.fpt.edu.vn.registerscreen;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 2/5/2018.
 */

public class Session {
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    Context ctx;
    String name;
    String password;

    public String getName() {
        name = sharedPreferences.getString("user","");
        return name;
    }
    public String getPassword(){
        password = sharedPreferences.getString("password","");
        return password;
    }

    public void setName(String name) {
        this.name = name;
        editor.putString("user", name).commit();
    }
    public void setPassword(String password) {
        this.password = password;
        editor.putString("password", password).commit();
    }
    public Session(Context ctx){
        this.ctx = ctx;
        sharedPreferences = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean loggedIn){
        editor.remove("LoggedInMode");
        editor.putBoolean("LoggedInMode", loggedIn);
        editor.commit();
    }

    public boolean LoggedIn(){
        return sharedPreferences.getBoolean("LoggedInMode", false);
    }
}
