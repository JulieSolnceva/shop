package study.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import julie.study.R;
import study.DBClasses.GoodDB;
import study.DBClasses.SectionDB;
import study.DataClasses.Good;
import study.DataClasses.Section;

public class SectionLoader extends AsyncTaskLoader<Object[]> {

    int section_id;
    Context context;
    public final static String ARGS_TITLE = "section_id";

    public SectionLoader(Context context, Bundle args) {
        super(context);
        this.context=context;
        if (args != null)
            section_id = args.getInt(ARGS_TITLE);
        else section_id=context.getResources().getInteger(R.integer.root_id);
    }

    @Override
    public Object[] loadInBackground() {
        Log.d("Loader", "loadInBackground  SectionLoader");

        ArrayList<Section> childData =new ArrayList<Section>();
        Section section=new Section();
        ArrayList<Good> goodData=new ArrayList<Good>();


        try {
            SectionDB sectionDB = new SectionDB(context);
            GoodDB goodDB =new GoodDB(context);

            childData=sectionDB.getChildren(section_id);
            sectionDB.id=section_id;
            section=sectionDB.init();

            goodDB.section_id=section_id;
            goodData= goodDB.getSectionGoods();
            sectionDB.closeDBConnect();
            goodDB.closeDBConnect();

        } catch (Exception e) {
            Log.d("Loader", "SectionLoader "+e.getMessage());
            return null;
        }
        Object rezult[]={childData, goodData, section};
        return rezult;
    }


}