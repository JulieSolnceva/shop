package study.DBClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Andrew on 30.10.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String LOG_TAG="DBHelper";

    private String dbFolder;
    public String dbPath;
    private Context context;
    private static final String DB_NAME = "bookShop";
    public  String dbName = "bookShop";

    public SQLiteDatabase myDataBase;

    public DBHelper(Context cont) {
        super(cont, DB_NAME, null, 1);
        context=cont;
        dbFolder= "/data/data/" + context.getPackageName() + "/databases/";
        dbPath=dbFolder+ dbName;
    }





    /**
     * Создает пустую базу данных и перезаписывает ее нашей собственной базой
     * */
    public void createDataBase() throws Exception {
        if(!checkDataBase())
            copyDataBase();
    }

    /**
     * Проверяет, существует ли уже эта база, чтобы не копировать каждый раз при запуске приложения
     * @return true если существует, false если не существует
     */
    private boolean checkDataBase(){
        File dbFile = new File(dbPath);
        return dbFile.exists();
    }


    private void copyDataBase() throws Exception{
          //Открываем локальную БД как входящий поток
            InputStream myInput = context.getAssets().open(dbName);

            //вызывая этот метод создаем пустую базу, позже она будет перезаписана
            this.getReadableDatabase();

            //Открываем пустую базу данных как исходящий поток
            OutputStream myOutput = new FileOutputStream(dbPath);
            //перемещаем байты из входящего файла в исходящий
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            //закрываем потоки
            myOutput.flush();
            myOutput.close();
            myInput.close();
    }

    public void openDataBase() throws SQLiteException {

           myDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db){}


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

}
