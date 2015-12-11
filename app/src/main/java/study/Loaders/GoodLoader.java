package study.Loaders;

        import android.content.AsyncTaskLoader;
        import android.content.Context;
        import android.os.Bundle;
        import android.util.Log;

        import study.DataClasses.Good;
        import study.DBClasses.GoodDB;
        import study.DataClasses.Section;

public class GoodLoader extends AsyncTaskLoader<Good> {

    int id;
    Context context;
    public final static String ARGS_TITLE = "id";

    public GoodLoader(Context context, Bundle args) {
        super(context);
        this.context=context;
        if (args != null)
            id = args.getInt(ARGS_TITLE);
       }

    @Override
    public Good loadInBackground() {
        Log.d("Loader", " GoodLoader");

        Good good=new Good();
        Section section=new Section();
        try {
            GoodDB goodDB =new GoodDB(context);
            //goodDB.getDBConnect();
            goodDB.id=id;
            good=goodDB.getGood();
            goodDB.closeDBConnect();

        } catch (Exception e) {
            return null;
        }
        return good;
    }

}