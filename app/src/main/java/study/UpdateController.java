package study;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

import julie.study.R;

public class UpdateController {
    boolean readyConnection =false;
    boolean readySDcard =false;
    boolean timeToUpdate =false;
    public boolean readyToUpdate;
    //Context context;

    public UpdateController(){

    }



    public void checkStartUpdate(Context context){
        checkTimeToUpdate();
        if(timeToUpdate) {
            checkInternetConnection(context);
            checkSDcard();
            if(readyConnection && readySDcard)
                readyToUpdate =true;
            else Toast.makeText(context, context.getString(R.string.error_update_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkTimeToUpdate(){
        //здесь будет проверка времени последнего обновления.
        // если нужно уже обновлять, возвращаем true
        timeToUpdate =true;
    }

    private void checkInternetConnection(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            readyConnection =true;
    }

    private void checkSDcard(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            readySDcard = true;
    }
}

