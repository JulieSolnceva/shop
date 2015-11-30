package julie.study.Loaders;

import java.util.ArrayList;
import java.util.Map;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import julie.study.R;
import julie.study.SectionDB;

public class LeftMenuLoader extends AsyncTaskLoader<Object[]> {

    int section_id;
    Context context;
    public final static String ARGS_TITLE = "section_id";

    public LeftMenuLoader(Context context, Bundle args) {
        super(context);
        this.context=context;
        if (args != null)
            section_id = args.getInt(ARGS_TITLE);
        else section_id=context.getResources().getInteger(R.integer.root_id);
    }

    @Override
    public Object[] loadInBackground() {
        Log.d("Loader", "LeftMenuLoader  ");

        ArrayList<Map<String, String>> groupData=new ArrayList<Map<String, String>>();
        ArrayList<ArrayList<Map<String, String>>> childData=new ArrayList<ArrayList<Map<String, String>>>();

        try {
            SectionDB sectionDB = new SectionDB(context);
            sectionDB.getDBConnect();
            sectionDB.getSectionDataForSimpleExpandableListAdapter();
            sectionDB.closeDBConnect();
            groupData=sectionDB.groupData;
            childData=sectionDB.childData;
        }
        catch (Exception e ) {
            return null;
        }
        Object rezult[]={groupData, childData};
        return rezult;
    }

}