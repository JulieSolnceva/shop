package julie.study.Fragments;

        import android.app.ActionBar;
        import android.app.Activity;
        import android.app.Fragment;
        import android.app.LoaderManager;
        import android.content.Loader;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;

        import java.util.ArrayList;

        import julie.study.Adapters.GoodAdapter;
        import julie.study.DataClasses.Author;
        import julie.study.DataClasses.Good;
        import julie.study.Loaders.AuthorLoader;
        import julie.study.R;


public class AuthorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object[]>  {
    private int id=1;
    Author author;
    ArrayList<Good> goodData=null;
    View view;
    TextView messageTxt;
    Good good;


    public static final String BUNDLE_CONTENT = "author_id";
    static final int LOADER_ID = 3;
    final String LOG_TAG = "states";

    public interface onAuthorClickListener {
       public void showGoodFragment(int id);
    }

    onAuthorClickListener authorClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            authorClickListener = (onAuthorClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onAuthorClickListener");
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey(BUNDLE_CONTENT)) {
            id= getArguments().getInt(BUNDLE_CONTENT);
        }
        Bundle bnd = new Bundle();
        bnd.putInt(BUNDLE_CONTENT, id);
        getLoaderManager().initLoader(LOADER_ID, bnd, this);

        view = inflater.inflate(R.layout.author_fragment, null);
        messageTxt =(TextView)view.findViewById(R.id.message);
        setRetainInstance(true);

        if(goodData==null) {
            Loader<Object[]> loader;
            loader = getLoaderManager().getLoader(LOADER_ID);
            messageTxt.setVisibility(View.VISIBLE);
            loader.forceLoad();
        }
        else{
            setActionBarTitle();
            drawAuthorInformation();
            drawGoodsList();
        }

        return view ;
    }

    @Override
    public Loader<Object[]> onCreateLoader(int id, Bundle args) {
        Loader<Object[]> loader = null;
        if (id == LOADER_ID) {
            loader = new AuthorLoader(getActivity(), args);
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
            goodData=(ArrayList<Good>)result[0];
            author=(Author) result[1];

            setActionBarTitle();
            drawAuthorInformation();
            drawGoodsList();
        }
        else showErrorMessage();
    }

    private void showErrorMessage(){
        messageTxt.setText(getString(R.string.db_error_message));
        messageTxt.setVisibility(View.VISIBLE);
    }

    private void setActionBarTitle() {
        if(author.title==null){
            showErrorMessage();
            return;
        }
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle(author.title);
    }




    private void drawGoodsList(){
        GoodAdapter adapter = new GoodAdapter(getActivity(), goodData);

       // goodList.setAdapter(adapter);


        LinearLayout listViewReplacement = (LinearLayout) view.findViewById(R.id.goods);
        listViewReplacement.removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, listViewReplacement);
            good=adapter.getGood(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    authorClickListener.showGoodFragment(good.id);
                }
            });
            listViewReplacement.addView(view);
        }




/*
        goodList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                authorClickListener.showGoodFragment(goodData.get(position).id);
            }
        });*/
    }

    private void drawAuthorInformation(){
        ((TextView) view.findViewById(R.id.title)).setText(author.title);
        ((TextView) view.findViewById(R.id.about)).setText(author.about);
    }

}

