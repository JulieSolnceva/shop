package study.DBClasses;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import study.DataClasses.Section;
import julie.study.R;

public class SectionDB extends DB {
    public int id;
    Context context;
    public ArrayList<Map<String, String>> groupData;
    public ArrayList<ArrayList<Map<String, String>>> childData;


    public SectionDB(Context context) throws Exception{
        super(context);
        this.context=context;
    }

    public Section init()
    {
      Section section=new Section();
      String sql="select title, left_id, right_id from section where _id="+id;
      Cursor cursor = dbActive.rawQuery(sql, null);
      if(cursor.moveToPosition(0)) {
          section.title = cursor.getString(0);
          section.left_id = cursor.getInt(1);
          section.right_id = cursor.getInt(2);
          section.id=id;
       }
       cursor.close();
       return section;
   }

   public ArrayList<Section> getChildren(int _id)
   {
       ArrayList<Section> children=new ArrayList<Section>();
       String sql="select c.title, c._id, count(t._id) as x "+
			"from  section as p, section as c, section as t "+
			"where p._id = "+ _id+
			"	and (c.left_id > p.left_id and c.right_id < p.right_id) "+
			"	and (t.left_id > p.left_id and t.right_id < p.right_id) "+
			"	and (t.left_id <= c.left_id and t.right_id >= c.right_id ) "+
			"group by c._id having x=1 "+
			"order by c.left_id";


       Cursor cursor = dbActive.rawQuery(sql, null);

      if (cursor.moveToFirst()) {
           do {
               Section section=new Section();
               section.title=cursor.getString(0);
               section.id=cursor.getInt(1);
               children.add(section);

           } while (cursor.moveToNext());
       }
       cursor.close();
       return children;
   }

    public ArrayList<Section> getParents(int _id)
    {
        ArrayList<Section> parents=new ArrayList<Section>();
        String sql="select b1.title, b1._id, (b1.right_id - b1.left_id) AS height "+
        "from section as b1, section as b2 "+
        "where b2.left_id between b1.left_id and b1.right_id "+
        "and b2.right_id between b1.left_id and b1.right_id "+
        "and b2._id= "+_id+
        " order by height desc";
        Cursor cursor = dbActive.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            cursor.moveToNext();
            do {
                Section section=new Section();
                section.title=cursor.getString(0);
                section.id=cursor.getInt(1);
                parents.add(section);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return parents;
    }


    public void getSectionDataForSimpleExpandableListAdapter() throws Exception {
        ArrayList<Section> children = getChildren(context.getResources().getInteger(R.integer.root_id));
        if (children.size() == 0) throw new Exception();

        // заполняем коллекцию групп из массива с названиями групп
        groupData = new ArrayList<Map<String, String>>();
        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();


        for (Section group : children) {
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("title", group.title);
            hash.put("id", String.valueOf(group.id));
            groupData.add(hash);

            ArrayList<Section> subChildren = getChildren(group.id);
            ArrayList<Map<String, String>> childDataItem = new ArrayList<Map<String, String>>();

            for (Section childItem : subChildren) {
                HashMap<String, String> sub = new HashMap<String, String>();
                sub.put("title", childItem.title);
                sub.put("id", String.valueOf(childItem.id));
                childDataItem.add(sub);
            }

            childData.add(childDataItem);
        }
    }

}



