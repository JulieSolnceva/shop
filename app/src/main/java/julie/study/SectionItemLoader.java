package julie.study;

import android.content.Loader;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;

public class SectionItemLoader extends Loader<Object[]> {

    final String LOG_TAG = "SectionItemLoader";
    public final static String ARGS_TITLE = "section_id";

    GetDataTask getDataTask;
    int section_id;
    Context context;

    public SectionItemLoader(Context context, Bundle args) {
        super(context);
        Log.d(LOG_TAG, hashCode() + " create SectionItemLoader");

        this.context=context;
        if (args != null)
            section_id = args.getInt("section_id");
        else section_id=context.getResources().getInteger(R.integer.root_id);
     }


    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        Log.d(LOG_TAG, hashCode() + " onForceLoad");
        if (getDataTask != null)
            getDataTask.cancel(true);
        getDataTask = new GetDataTask();
        getDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, section_id);
    }

     void getResultFromTask(Object... result) {
        deliverResult(result);
    }

    class GetDataTask extends AsyncTask<Integer, Void, Object[]> {

        @Override
        protected Object[] doInBackground(Integer... params) {
            ArrayList<Section> childData =new ArrayList<Section>();
            Section section=new Section();
            ArrayList<Good> goodData=new ArrayList<Good>();

            try {
                SectionDB sectionDB = new SectionDB(context);
                sectionDB.getDBConnect();
                GoodDB goodDB =new GoodDB(context);
                goodDB.getDBConnect();

                childData=sectionDB.getChildren(params[0]);
                sectionDB.id=params[0];
                section=sectionDB.init();
                goodDB.id_section=params[0];
                goodData= goodDB.getSectionGoods();

                sectionDB.closeDBConnect();
                goodDB.closeDBConnect();

            } catch (Exception e) {
                return null;
            }
            Object rezult[]={childData, goodData, section};
            return rezult;
        }

        @Override
        protected void onPostExecute(Object... result) {
            super.onPostExecute(result);
            getResultFromTask(result);
        }
    }
}
