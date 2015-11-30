package julie.study;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DBTest  {



    @Mock
    Context mMockContext;


    @Test(expected = SQLiteException.class)
    public void testGetDBConnectExeption() throws Exception {

        DB db=new DB(mMockContext);
      // DB db1=new DB(mMockContext);
      // Assert.assertSame("Erre",db1, dbActive);
        db.dbName="dbjkjk";

    }

    @Test
    public void testGetDBConnect() {
        DB db=new DB(mMockContext);
        db.getDBConnect();
        Assert.assertTrue(db.dbActive.isOpen());
        }
}