package julie.study.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import julie.study.R;
import julie.study.DBClasses.SectionDB;


public class MainActivity extends Activity {


    ArrayList<Map<String, String>> groupData;
    ArrayList<ArrayList<Map<String, String>>> childData;
    ExpandableListView elvMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseTask task = new DataBaseTask();
        task.execute();
    }

        private void drawList(){
            if (groupData==null) {
                TextView myTextView = (TextView) findViewById(R.id.message);
                myTextView.setText(getResources().getString(R.string.no_section_message));
                return;
            }

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
                    groupData,
                    android.R.layout.simple_expandable_list_item_1,
                    groupFrom,
                    groupTo,
                    childData,
                    android.R.layout.simple_list_item_1,
                    childFrom,
                    childTo);

            elvMain = (ExpandableListView) findViewById(R.id.elvMain);
            elvMain.setAdapter(adapter);

            // нажатие на элемент
            elvMain.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition,   int childPosition, long id) {
                    goToSectionItem(Integer.parseInt(childData.get(groupPosition).get(childPosition).get("id")));
                    return false;
                }
            });

            // нажатие на группу
            elvMain.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    if (childData.get(groupPosition).size() == 0){
                        goToSectionItem(Integer.parseInt(groupData.get(groupPosition).get("id")));
                        return true;
                    }
                    return false;
                }
            });

        }

    private void goToSectionItem(int id){
        Intent intent = new Intent(this, SectionItemActivity.class);
        intent.putExtra("sectionId", id);
        startActivity(intent);
    }

// todo ПЕРЕДЕЛАТЬ ПОД LOADER !!!!!!!!!!!!!

    class DataBaseTask extends AsyncTask<Void, Void, Void> {

        ArrayList<Map<String, String>> taskGroupData;
        ArrayList<ArrayList<Map<String, String>>> taskChildData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
                   }

        @Override
        protected Void doInBackground(Void... params) {
            try{
                SectionDB sectionDB = new SectionDB(MainActivity.this);
              //  sectionDB.getDBConnect();
                sectionDB.getSectionDataForSimpleExpandableListAdapter();
                sectionDB.closeDBConnect();
                taskGroupData=sectionDB.groupData;
                taskChildData=sectionDB.childData;
            }
            catch (Exception e ) {
                // todo  здесь нужно сплывающее окно с текстом ошибки
                Log.d("main  ","подразделов нет ");

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.d("main  ","groupData size= "+taskGroupData.size());
            MainActivity.this.groupData=taskGroupData;
            MainActivity.this.childData=taskChildData;
            MainActivity.this.drawList();
         }


    }



}
