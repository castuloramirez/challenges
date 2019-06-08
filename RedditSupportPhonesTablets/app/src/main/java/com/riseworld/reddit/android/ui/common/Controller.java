package com.riseworld.reddit.android.ui.common;

import android.content.Context;

import com.riseworld.reddit.android.ui.pojo.DataObject;
import com.riseworld.reddit.android.ui.util.ProccessData;
import com.riseworld.reddit.android.util.GlobalREDDIT;

import java.util.ArrayList;

/**
 * Controls the data between the services and teh UI.
 **/
public class Controller {

    ArrayList<DataObject> listData;

    public ArrayList<DataObject> getListData() {
        return listData;
    }

    public void setListData(ArrayList<DataObject> listData) {
        this.listData = listData;
    }

    public ArrayList<DataObject> getDataSet() {
        this.setListData(ProccessData.getDataSet());
        return listData;
    }


    /**
     * @param pContext Current Activity
     * @param pListData List with the data.
     */
    public void fillDatabase(Context pContext, ArrayList<DataObject> pListData) {
        final GlobalREDDIT globalOCTAVE = (GlobalREDDIT) pContext.getApplicationContext();
        if ((pListData != null) && (pListData.size() > 0)) {
            globalOCTAVE.getRedditRiseDBHandler().deleteAllConnections();
            for (int index = 0; index < 20; index++) {
                DataObject obj = pListData.get(index);
                globalOCTAVE.connect(obj.getTitle(), obj.getAutor(), obj.getUrl(),obj.getPermalink());
            }
        }
    }

    public ArrayList<DataObject> getDataSetFromDB(Context pContext) {
        final GlobalREDDIT globalOCTAVE = (GlobalREDDIT) pContext.getApplicationContext();
        ArrayList<DataObject> pListData = globalOCTAVE.getRedditRiseDBHandler().getAllConnections();
        this.setListData(pListData);
        return listData;
    }

    public String [] getDataSetAsStringArray(){
        ArrayList<DataObject> pListData =  getDataSet();
        if ((pListData != null) && (pListData.size() > 0)) {
            String[] dataString = new String[20];
            for (int index = 0; index < 20; index++) {
                DataObject obj = pListData.get(index);
                dataString[index]=obj.getTitle();
            }
            return dataString;
        }
        return new String[1];
    }




    public String [] getDataSetAsStringArrayFromDB(){
        if ((getListData() != null) && (getListData().size() > 0)) {
            String[] dataString = new String[20];
            for (int index = 0; index < 20; index++) {
                DataObject obj = getListData().get(index);
                dataString[index]=obj.getTitle();
            }
            return dataString;
        }
        return new String[1];
    }

    public String getURL(int detailItemPosition){
        int positionIndex = detailItemPosition-1;
        if(positionIndex>=0){
            return this.getListData().get((positionIndex)).getUrl();
        }else{
            return "www.google.com";
        }
    }
    public String getPermalink(int detailItemPosition){
        int positionIndex = detailItemPosition-1;
        if(positionIndex>=0){
            return this.getListData().get((positionIndex)).getPermalink();
        }else{
            return "www.google.com";
        }
    }

}