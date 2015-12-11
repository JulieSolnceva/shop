package study.DBClasses;

import android.content.Context;
import android.database.Cursor;

import study.DataClasses.Author;

public class AuthorDB extends DB {

    public int id;

    public AuthorDB(Context context) throws Exception{
        super(context);
    }

    public Author init(){
        Author author;
        String sql="select title, about from author where _id="+this.id;

        Cursor cursor = dbActive.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            author= new Author();
            author.title=cursor.getString(0);
            author.about=cursor.getString(1);
            author.id=this.id;
        }
        else author=null;
        cursor.close();
        return author;
    }
}
