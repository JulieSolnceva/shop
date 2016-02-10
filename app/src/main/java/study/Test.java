package study;

import android.content.Context;
import android.util.Log;

import julie.study.R;

/**
 * Created by Юлия on 23.12.2015.
 */
public class Test {

    public static void start(Context c){
        int id=c.getResources().getInteger(R.integer.root_id);

        Log.d("Test",Integer.toString(id));

    }
}
