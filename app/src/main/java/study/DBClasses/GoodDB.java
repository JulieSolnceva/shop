package study.DBClasses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import study.DataClasses.Good;

public class GoodDB{
    private SQLiteDatabase dbActive;
    public int id, author_id, section_id;

    public GoodDB(Context context)  throws Exception{
        dbActive=DB.getInstance(context).dbActive;
    }


    public ArrayList<Good> getSectionGoods(){
        ArrayList<Good> goods= new ArrayList<Good>();

        String sql="select g.title, g._id, a.title as author,g.price " +
                "from good as g " +
                "left join author as a on a._id=g.author_id " +
                "where g.section_id="+this.section_id+" order by g.title";
        Cursor cursor = dbActive.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Good good=new Good();
                good.title=cursor.getString(0);
                good.id=cursor.getInt(1);
                good.author=cursor.getString(2);
                good.price=cursor.getFloat(3);
                goods.add(good);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return goods;
    }

    public ArrayList<Good> getAuthorGoods(){
        ArrayList<Good> goods= new ArrayList<Good>();
        String sql="select g.title, g._id, a.title as author,g.price " +
                "from good as g " +
                "left join author as a on a._id=g.author_id " +
                "where g.author_id="+this.author_id+" order by g.title";
        Cursor cursor = dbActive.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Good good=new Good();
                good.title=cursor.getString(0);
                good.id=cursor.getInt(1);
                good.author=cursor.getString(2);
                good.price=cursor.getFloat(3);
                goods.add(good);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return goods;
    }

    public Good getGood(){
        Good good;
        String sql="select g.title, g.publish_year, g.about, g.pages, g.price, a.title as author, " +
                "g.author_id,  p.title as publish, g.publish_id, s.title as section " +
                "from good as g " +
                "left join author as a on a._id=g.author_id " +
                "left join publish as p on p._id=g.publish_id " +
                "left join section as s on s._id=g.section_id " +
                "where g._id="+this.id;

        Cursor cursor = dbActive.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
                good= new Good();
                good.title=cursor.getString(0);
                good.publish_year=cursor.getInt(1);
                good.about=cursor.getString(2);
                good.pages=cursor.getInt(3);
                good.price=cursor.getFloat(4);
                good.author=cursor.getString(5);
                good.author_id=cursor.getInt(6);
                good.publish=cursor.getString(7);
                good.publish_id=cursor.getInt(8);
                good.section_title=cursor.getString(9);
                good.id=this.id;
        }
        else good=null;
        cursor.close();
        return good;
    }

}
