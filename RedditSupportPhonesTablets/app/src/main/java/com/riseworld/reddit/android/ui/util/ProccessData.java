package com.riseworld.reddit.android.ui.util;

import com.github.jreddit.entity.Submission;
import com.riseworld.reddit.android.services.RedditRiseService;
import com.riseworld.reddit.android.ui.pojo.DataObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Handlers result of the web service
 **/
public class ProccessData {
    public static ArrayList<DataObject> getDataSet(){
        List<Submission> submissionList = RedditRiseService.getSubmissionListService();
        ArrayList<DataObject> results = new ArrayList<>();
        if (submissionList.size() > 0) {
            for (int index = 0; index < 20; index++) {
                Submission s = submissionList.get(index);
                DataObject obj = new DataObject(s.getTitle(),
                        s.getAuthor());
                obj.setUrl(s.getURL());
                obj.setPermalink(s.getPermalink());
                results.add(index, obj);
            }
        }
        return results;
    }
}