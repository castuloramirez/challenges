package com.riseworld.reddit.android.services;


import com.github.jreddit.entity.Submission;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.PoliteHttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Handlers the web services that call to the Reddit portal
 **/

public class RedditRiseService {

    public static List<Submission> getSubmissionListService() {
        List<Submission> submissionList = null;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletionService<List<Submission>> completionService = new ExecutorCompletionService<>(executorService);
        completionService.submit(new Callable<List<Submission>>() {
            @Override
            public List<Submission> call() throws Exception {
                RestClient restClient = new PoliteHttpRestClient();
                restClient.setUserAgent("bot/1.0 by name");
                Submissions subms = new Submissions(restClient);
                // Retrieve submissions of a submission
                //return subms.ofSubreddit("AskReddit", SubmissionSort.HOT, -1, 25, null, null, true);
                return subms.ofSubreddit("programming", SubmissionSort.HOT, -1, 25, null, null, true);
            }
        });
        try {
            final Future<List<Submission>> completedFuture = completionService.take();
            submissionList = completedFuture.get();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return submissionList;
    }
}
