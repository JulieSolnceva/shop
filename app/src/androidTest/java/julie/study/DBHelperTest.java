package julie.study;

import android.test.AndroidTestCase;
import android.util.Log;

import julie.study.DBClasses.DBHelper;

/*
чтобы работали тесты, нужно:
1. в strings.xml  в переменной db_mode задать значение 'test',
2. удалить приложение на устройстве для тестирования, чтобы там не было старой БД
 */

public class DBHelperTest extends AndroidTestCase {



    public void testCreateDataBaseExeption() throws Exception {
        DBHelper dbHelper = new DBHelper(mContext);
        try {
          dbHelper.dbName = "bdError";
          dbHelper.createDataBase();

         // !!!!! чтобы пройти этот тест, нужно перед тестом удалить приложение с устройства
          assertTrue(false);
         }
           catch (Exception e){
               Log.d("DBHelperTest", "testCreateDataBaseExeption ошибка = "+e.getMessage());
               assertTrue(true);
          }
        finally {
            dbHelper.close();
        }
    }

    public void testOpenDataBaseExeption() throws Exception {
        DBHelper dbHelper = new DBHelper(mContext);
        try {

            dbHelper.createDataBase();
            dbHelper.dbPath="errorPath";

            dbHelper.openDataBase();
            assertTrue(false);
        }
        catch (Exception e){
            Log.d("DBHelperTest", "testOpenDataBaseExeption ошибка = "+e.getMessage());
            assertTrue(true);

        }
        finally {
          dbHelper.close();
        }
    }
}