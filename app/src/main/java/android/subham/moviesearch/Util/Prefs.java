package android.subham.moviesearch.Util;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Subham on 06-03-2018.
 */

public class Prefs
{
    SharedPreferences preferences;
    public Prefs(Activity activity){
        preferences = activity.getPreferences(activity.MODE_PRIVATE);

    }

    public void setSearch(String search){
        preferences.edit().putString("search",search).apply();
    }
    public String getSearch()
    {
        return preferences.getString("search","Batman");
    }
}
