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
import android.widget.LinearLayout;
import android.widget.TextView;
import julie.study.Good;
import julie.study.Loaders.GoodLoader;
import julie.study.R;

public class GoodFragment extends Fragment implements LoaderManager.LoaderCallbacks<Good>{

    public int id;
    public static final String BUNDLE_CONTENT = "id";
    static final int LOADER_ID = 3;
    View view;
    TextView messageTxt;
    private Good good;

    public interface onGoodClickListener {
        public void openAuthorActivity(int id);
    }

    onGoodClickListener clickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            clickListener = (onGoodClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onGoodClickListener");
        }
    }

   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey(BUNDLE_CONTENT)) {
            id= getArguments().getInt(BUNDLE_CONTENT);
        }
        Bundle bnd = new Bundle();
        bnd.putInt(GoodLoader.ARGS_TITLE, id);
        getLoaderManager().initLoader(LOADER_ID, bnd, this);
        view = inflater.inflate(R.layout.good_fragment, null);
        messageTxt =(TextView)view.findViewById(R.id.message);
        setRetainInstance(true);

        if(good==null) {
            Loader<Good> loader;
            loader = getLoaderManager().getLoader(LOADER_ID);
            messageTxt.setVisibility(View.VISIBLE);
            loader.forceLoad();
        }
        else{
            setActionBarTitle();
            drawGood();
        }

        return view ;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Good> onCreateLoader(int id, Bundle args) {
        Loader<Good> loader = null;
        if (id == LOADER_ID)
            loader = new GoodLoader(getActivity(), args);
        return loader;
    }

    @Override
    public void onLoaderReset(Loader<Good> loader) {
    }

    @Override
    public void onLoadFinished(Loader<Good> loader,Good result) {
        messageTxt.setVisibility(View.GONE);
        if(result!=null) {
            good=result;
            setActionBarTitle();
            drawGood();
        }
        else showErrorMessage();
    }

    private void showErrorMessage(){
        messageTxt.setText(getString(R.string.db_error_message));
        messageTxt.setVisibility(View.VISIBLE);
    }

    private void setActionBarTitle() {
        if(good.section_title==null){
            showErrorMessage();
            return;
        }
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle(good.section_title);
    }

    private void drawGood(){
        TextView txt_price,txt_title,txt_author, txt_publish, txt_year_publish, txt_pages,txt_about;
        LinearLayout str_author, str_publish, str_year_publish, str_pages;

        txt_price= (TextView)view.findViewById(R.id.price);
        txt_title=(TextView)view.findViewById(R.id.title);
        txt_author=(TextView) view.findViewById(R.id.author);
        str_author=(LinearLayout) view.findViewById(R.id.str_author);
        str_publish=(LinearLayout)view.findViewById(R.id.str_publish);
        txt_publish=(TextView)view.findViewById(R.id.publish);
        str_year_publish=(LinearLayout)view.findViewById(R.id.str_year_publish);
        txt_year_publish=(TextView)view.findViewById(R.id.year_publish);
        str_pages=(LinearLayout)view.findViewById(R.id.str_pages);
        txt_pages=(TextView)view.findViewById(R.id.pages);
        txt_about=   (TextView)view.findViewById(R.id.about);


        txt_price.setText(good.price+"  "+getActivity().getString(R.string.currency));
        txt_title.setText(good.title);

        if(good.author!=null) {
            str_author.setVisibility(View.VISIBLE);
            txt_author.setText(good.author);
            str_author.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.openAuthorActivity(good.id_author);
                }
            });
        }
        if(good.publish!=null){
            str_publish.setVisibility(View.VISIBLE);
           txt_publish.setText(good.publish);
        }
        if(good.year_publish!=0){
            str_year_publish.setVisibility(View.VISIBLE);
            txt_year_publish.setText(String.valueOf(good.year_publish));
        }
        if(good.pages!=0){

            Log.d("GoodFragment", "good.pages= " + good.pages);
            str_pages.setVisibility(View.VISIBLE);
            txt_pages.setText(String.valueOf(good.pages));
        }
        txt_about.setText(good.about);
    }

}
