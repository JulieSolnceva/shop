package julie.study;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class GoodDB extends DB{
    Context context;
    public int id_section, id;


    public GoodDB(Context context){
        super(context);
    }


    public ArrayList<Good> getSectionGoods(){
        ArrayList<Good> goods= new ArrayList<Good>();

        String sql="select g.title, g._id, a.title as author,g.price " +
                "from good as g " +
                "left join author as a on a._id=g.id_author " +
                "where g.id_section="+this.id_section+" order by g.title";

        Cursor cursor = dbActive.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Good good=new Good();
                good.title=cursor.getString(0);
                good.id=cursor.getInt(1);
                good.author=cursor.getString(2);
                good.price=cursor.getFloat(3);
                Log.d( "GoodDB", "good.author= "+good.author);
                goods.add(good);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return goods;
    }

    public Good getGood(){
        Good good= new Good();
        String sql="select g.title, g.year_publish, g.about, g.pages, g.price, a.title as author, " +
                "g.id_author,  p.title as publish, g.id_publish, s.title as section " +
                "from good as g " +
                "left join author as a on a._id=g.id_author " +
                "left join publish as p on p._id=g.id_publish " +
                "left join section as s on s._id=g.id_section " +
                "where g._id="+this.id;

        Cursor cursor = dbActive.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
                good= new Good();
                good.title=cursor.getString(0);
                good.year_publish=cursor.getInt(1);
                good.about=cursor.getString(2);
                good.pages=cursor.getInt(3);
                good.price=cursor.getFloat(4);
                good.author=cursor.getString(5);
                good.id_author=cursor.getInt(6);
                good.publish=cursor.getString(7);
                good.id_publish=cursor.getInt(8);
                good.section_title=cursor.getString(9);
                good.id=this.id;
        }
        else good=null;
        cursor.close();
        return good;
    }

}
