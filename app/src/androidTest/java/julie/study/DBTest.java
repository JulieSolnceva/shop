package julie.study;

import android.test.AndroidTestCase;
import android.util.Log;

import study.DBClasses.DB;

/*
чтобы работали тесты, нужно:
 1. в strings.xml  в переменной db_mode задать значение 'test',
2. удалить приложение на устройстве для тестирования, чтобы там не было старой БД
 */

public class DBTest extends AndroidTestCase {


    public void testGetDBConnect() throws Exception{
        DB db=new DB(mContext);
        db.getDBConnect();

        assertTrue(db.dbActive.isOpen());

        db.dbActive.close();
        Log.d("test","DBTest testGetDBConnect ok");
    }


}
