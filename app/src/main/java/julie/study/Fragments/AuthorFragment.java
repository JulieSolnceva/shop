
package julie.study.Fragments;

        import android.app.ActionBar;
        import android.app.Activity;
        import android.app.Fragment;
        import android.app.LoaderManager;
        import android.content.Loader;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;

        import java.util.ArrayList;
        import julie.study.Good;
        import julie.study.GoodAdapter;
        import julie.study.Loaders.SectionLoader;
        import julie.study.R;
        import julie.study.Section;
        import julie.study.SectionItemLoader;

public class AuthorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object[]> {
    private int id=1;
    Author author;
    ArrayList<Good> goodData=null;
    ListView sectionList;
    ListView goodList;
    TextView messageTxt;
    Section section;

    public static final String BUNDLE_CONTENT = "section_id";
    static final int LOADER_ID = 2;
    final String LOG_TAG = "states";

    public interface onSectionClickListener {
        public void showSectionFragment(int id);
        public void showGoodFragment(int id);
    }

    onSectionClickListener sectionClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            sectionClickListener = (onSectionClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSectionClickListener");
        }
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey(BUNDLE_CONTENT)) {
            id= getArguments().getInt(BUNDLE_CONTENT);
        }
        Bundle bnd = new Bundle();
        bnd.putInt(SectionItemLoader.ARGS_TITLE, id);
        getLoaderManager().initLoader(LOADER_ID, bnd, this);

        View view = inflater.inflate(R.layout.section_fragment, null);
        sectionList= (ListView) view.findViewById(R.id.sections);
        goodList= (ListView) view.findViewById(R.id.goods);
        messageTxt =(TextView)view.findViewById(R.id.message);

        setRetainInstance(true);

        if(childData==null) {
            Loader<Object[]> loader;
            loader = getLoaderManager().getLoader(LOADER_ID);
            messageTxt.setVisibility(View.VISIBLE);
            loader.forceLoad();
        }
        else{
            setActionBarTitle();
            drawChildList();
            drawGoodsList();
        }

        return view ;
    }


    @Override
    public Loader<Object[]> onCreateLoader(int id, Bundle args) {
        Loader<Object[]> loader = null;
        if (id == LOADER_ID) {
            loader = new SectionLoader(getActivity(), args);
        }
        return loader;
    }

    @Override
    public void onLoaderReset(Loader<Object[]> loader) {
    }

    @Override
    public void onLoadFinished(Loader<Object[]> loader, Object[] result) {
        messageTxt.setVisibility(View.GONE);

        if(result!=null) {
            childData=(ArrayList<Section>)result[0];
            goodData=(ArrayList<Good>)result[1];
            section=(Section) result[2];

            setActionBarTitle();
            drawChildList();
            drawGoodsList();
        }
        else showErrorMessage();
    }

    private void showErrorMessage(){
        messageTxt.setText(getString(R.string.db_error_message));
        messageTxt.setVisibility(View.VISIBLE);
    }

    private void setActionBarTitle() {
        if(section.title==null){
            showErrorMessage();
            return;
        }
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle(section.title);
    }

    private void drawChildList(){
        ArrayList<String> title =new ArrayList<String>();
        for(Section sectionTmp: childData){
            title.add(sectionTmp.title);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, title);
        sectionList.setAdapter(adapter);

        sectionList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                sectionClickListener.showSectionFragment(childData.get(position).id);
            }
        });

    }


    private void drawGoodsList(){
        GoodAdapter adapter = new GoodAdapter(getActivity(), goodData);
        goodList.setAdapter(adapter);

        goodList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                sectionClickListener.showGoodFragment(goodData.get(position).id);
            }
        });
    }



}

