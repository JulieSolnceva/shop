package study.DBClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import study.Adapters.AdapterForTestDB;
import julie.study.R;

public class DB {
    private static DB uniqueInstance;

    public SQLiteDatabase dbActive;
    private DBHelper dbHelper;
    private Context context;
    private static final String TEST_FILE_PREFIX = "test_";

    private DB(Context c) throws Exception {
        context=c;
        dbHelper=null;
        getDBConnect();
    }

    public static synchronized DB getInstance(Context c) throws Exception{
        if(uniqueInstance==null){
                              uniqueInstance=new DB(c) ;

        }
        return uniqueInstance;
    }

    public void getDBConnect() throws Exception {
       try {
           if (context.getString(R.string.db_mode).equals("develop")) {

               Log.d("DB", "develop");
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
          Log.d("DB error", e.getMessage());
          throw new Exception(e.getMessage());
       }
    }

    public void closeDBConnect(){
        if(dbHelper!=null)
            dbHelper.close();
    }
}


