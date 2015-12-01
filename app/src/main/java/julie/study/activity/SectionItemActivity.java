package julie.study.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.Map;

import julie.study.DataClasses.Author;
import julie.study.DataClasses.DataForLeftMenu;
import julie.study.ErrorMessage;
import julie.study.Fragments.AuthorFragment;
import julie.study.Fragments.GoodFragment;
import julie.study.Fragments.SectionFragment;
import julie.study.Loaders.LeftMenuLoader;
import julie.study.R;

public class SectionItemActivity extends FragmentActivity
        implements SectionFragment.onSectionClickListener,
                    GoodFragment.onGoodClickListener,
                     AuthorFragment.onAuthorClickListener,
                    LoaderManager.LoaderCallbacks<Object[]>{

    int section_id, id, back_id;
    DrawerLayout mDrawerLayout;
    ExpandableListView mDrawerList ;
    private ActionBarDrawerToggle mDrawerToggle;
    static final int LOADER_ID = 1;
    ArrayList<Map<String, String>> drawerListGroupData;
    ArrayList<ArrayList<Map<String, String>>>  drawerListChildData;
    String fragment, back_fragment=null;

    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_item);
        Intent intent = getIntent();
        section_id = intent.getIntExtra("sectionId",getResources().getInteger(R.integer.root_id));

        if(savedInstanceState!=null) {
            fragment = savedInstanceState.getString("fragment");
            id = savedInstanceState.getInt("id");
            back_fragment = savedInstanceState.getString("back_fragment");
            back_id = savedInstanceState.getInt("back_id");
        }
        else {
            fragment="section";
            id=section_id;
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
                 );
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        drawerListGroupData = new ArrayList<Map<String, String>>();
        drawerListChildData = new ArrayList<ArrayList<Map<String, String>>>();

        DataForLeftMenu dataForLeftMenu = (DataForLeftMenu) getLastCustomNonConfigurationInstance();
        if(dataForLeftMenu!=null){
            drawerListGroupData=dataForLeftMenu.drawerListGroupData;
            drawerListChildData=dataForLeftMenu.drawerListChildData;
            setDrawerList();
        }
        else {
            getLoaderManager().initLoader(LOADER_ID, null, this);
            Loader<Object[]> loader;
            loader = getLoaderManager().getLoader(LOADER_ID);
            loader.forceLoad();
        }

        ActionBar actionBar = getActionBar();
        actionBar.setIcon(new ColorDrawable(0));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("");

       switch (fragment){
           case  "section":
               showSectionFragment(id);
               break;
           case "good":
               showGoodFragment(id);
               break;
           case  "author":
               showAuthorFragment(id);
               break;
       }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fragment", fragment);
        outState.putInt("id",id);
        outState.putString("back_fragment", back_fragment);
        outState.putInt("back_id",back_id);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
      /* логика настоена так: запоминается только один предыдущий фрагмент:
     нажатие back -> открывается предыдущий фрагмент,
     следующее нажатие back ->открывается предыдущее activity */

        if(back_fragment.equals(fragment) && back_id==id)
          super.onBackPressed();
        else clickBackOnFragment();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_section_item, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public Object onRetainCustomNonConfigurationInstance() {
        DataForLeftMenu dataForLeftMenu=new DataForLeftMenu();
        dataForLeftMenu.setData(drawerListGroupData, drawerListChildData);
        return dataForLeftMenu;
    }

    @Override
    public Loader<Object[]> onCreateLoader(int id, Bundle args) {
        Loader<Object[]> loader = null;
        if (id == LOADER_ID) {
            loader = new LeftMenuLoader(this, args);
        }
        return loader;
    }

    @Override
    public void onLoaderReset(Loader<Object[]> loader) {
    }

    @Override
    public void onLoadFinished(Loader<Object[]> loader, Object[] result) {
        if(result!=null) {
            drawerListGroupData=(ArrayList<Map<String, String>>)result[0];
            drawerListChildData=(ArrayList<ArrayList<Map<String, String>>>)result[1];
            setDrawerList();
        }
        else ErrorMessage.show(); //todo написать этот метод!!!!
    }

    public void showSectionFragment(int id){
        if(fragment!="section"|| this.id!=id)
         setBackDataForFragment();
        fragment="section";
        this.id=id;

        String fragmentTag="section"+id;
        Bundle bundle = new Bundle();
        bundle.putInt(SectionFragment.BUNDLE_CONTENT, id);

        SectionFragment currFragment=null;

        if(fragmentTag!="")
            currFragment = (SectionFragment) getFragmentManager().findFragmentByTag(fragmentTag);

        if (currFragment == null) {
            currFragment = new SectionFragment();
            currFragment.setRetainInstance(true);
            currFragment.setArguments(bundle);
            FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frgmCont, currFragment, "section"+id);
            fTrans.commit();
        }
    }

    public void showGoodFragment(int id){
       if(fragment!="good"|| this.id!=id)
        setBackDataForFragment();

       fragment="good";
       this.id=id;

        Bundle bundle = new Bundle();
        bundle.putInt(GoodFragment.BUNDLE_CONTENT, id);
        String fragmentTag="good"+id;
        GoodFragment currFragment=null;

        if(fragmentTag!="")
            currFragment = (GoodFragment) getFragmentManager().findFragmentByTag(fragmentTag);

        if (currFragment == null) {
            currFragment = new GoodFragment();
            currFragment.setRetainInstance(true);
            currFragment.setArguments(bundle);
            FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frgmCont, currFragment, "good"+id);
            fTrans.commit();
        }
    }

    public void showAuthorFragment(int id){
        if(fragment!="author"|| this.id!=id)
            setBackDataForFragment();

        fragment="author";
        this.id=id;

        Bundle bundle = new Bundle();
        bundle.putInt(AuthorFragment.BUNDLE_CONTENT, id);
        String fragmentTag="author"+id;
        AuthorFragment currFragment=null;

        if(fragmentTag!="")
            currFragment = (AuthorFragment) getFragmentManager().findFragmentByTag(fragmentTag);

        if (currFragment == null) {
            currFragment = new AuthorFragment();
            currFragment.setRetainInstance(true);
            currFragment.setArguments(bundle);
            FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frgmCont, currFragment, "author"+id);
            fTrans.commit();
        }
    }

    private  void setBackDataForFragment(){
           back_fragment = fragment;
           back_id = id;
    }

    public void clickBackOnFragment(){

        switch (back_fragment){
            case "section":
                showSectionFragment(back_id);
                break;
            case "good":
                showGoodFragment(back_id);
                break;
        }
        back_fragment=fragment;
        back_id=id;
    }

    private void setDrawerList() {
        // список аттрибутов групп для чтения
        String groupFrom[] = new String[] {"title","_id"};
        // список ID view-элементов, в которые будет помещены аттрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};
        // список аттрибутов элементов для чтения
        String childFrom[] = new String[] {"title","_id"};
        // список ID view-элементов, в которые будет помещены аттрибуты элементов
        int childTo[] = new int[] {android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                drawerListGroupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                drawerListChildData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition,   int childPosition, long id) {
                showSectionFragment(Integer.parseInt(drawerListChildData.get(groupPosition).get(childPosition).get("id")));
                return false;
            }
        });

         mDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                if (drawerListChildData.get(groupPosition).size() == 0){
                    showSectionFragment(Integer.parseInt(drawerListGroupData.get(groupPosition).get("id")));
                    return true;
                }
                return false;
            }
        });
    }
}



