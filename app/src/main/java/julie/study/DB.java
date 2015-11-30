package julie.study;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

public class DB {
    public SQLiteDatabase dbActive;
    private DBHelper dbHelper;
    private Context context;
    private static final String TEST_FILE_PREFIX = "test_";

    public DB(Context c) {
        context=c;
        dbHelper=null;
    }

    public void getDBConnect() throws Exception {
       try {
           if (context.getString(R.string.db_mode).equals("develop")) {
               dbHelper = new DBHelper(context);
               dbHelper.createDataBase();
               dbHelper.openDataBase();
               dbActive = dbHelper.getWritableDatabase();
           }

           if (context.getString(R.string.db_mode).equals("test")) {
               RenamingDelegatingContext contextR
                       = new RenamingDelegatingContext(context, TEST_FILE_PREFIX);

               AdapterForTestDB testAdapter = new AdapterForTestDB(contextR);
               testAdapter.open();
               dbActive = testAdapter.db;
           }
       }
       catch(Exception e){
          Log.d("DB", e.getMessage());
          throw new Exception(context.getString(R.string.db_error_message));
       }
    }

    public void closeDBConnect(){
        if(dbHelper!=null)
            dbHelper.close();
    }
}


