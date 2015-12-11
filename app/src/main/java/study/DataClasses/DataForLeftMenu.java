package study.DataClasses;

import java.util.ArrayList;
import java.util.Map;

public class DataForLeftMenu {

    public ArrayList<Map<String, String>> drawerListGroupData;
    public ArrayList<ArrayList<Map<String, String>>>  drawerListChildData;

    public void setData(ArrayList<Map<String, String>> groupData, ArrayList<ArrayList<Map<String, String>>> childData){
        drawerListGroupData=groupData;
        drawerListChildData=childData;
    }
}
