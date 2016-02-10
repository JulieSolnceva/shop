package study.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import study.DBClasses.AuthorDB;
import study.DBClasses.GoodDB;
import study.DataClasses.Author;
import study.DataClasses.Good;

/**
 * Created by Юлия on 01.12.2015.
 */
public class AuthorLoader extends AsyncTaskLoader<Object[]> {
    int author_id;
    Context context;
    public final static String ARGS_TITLE = "author_id";

    public AuthorLoader(Context context, Bundle args) {
        super(context);
        this.context=context;
        if (args != null)
            author_id = args.getInt(ARGS_TITLE);
        else author_id =0;
    }

    @Override
    public Object[] loadInBackground() {
        Log.d("Loader", "loadInBackground  AuthorLoader");

        Author author = new Author();
        ArrayList<Good> goodData=new ArrayList<Good>();

        try {
            AuthorDB authorDB = new AuthorDB(context);
            GoodDB goodDB =new GoodDB(context);

            authorDB.id= author_id;
            author=authorDB.init();
            goodDB.author_id= author_id;
            goodData= goodDB.getAuthorGoods();
        } catch (Exception e) {
            return null;
        }
        Object rezult[]={goodData, author};
        return rezult;
    }
}
