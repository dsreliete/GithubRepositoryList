package com.challenge.concrete.concretegithubrepolist.data;

import com.challenge.concrete.concretegithubrepolist.data.model.PullRequest;
import com.challenge.concrete.concretegithubrepolist.data.model.Repository;

import java.util.List;

/**
 * Created by eliete on 8/25/16.
 */
public interface DataRepository {

    interface getRepoListOnFinishedListener {
        void onFinishedList(List<Repository> list);
    }

    void getRepositoryList(int page, getRepoListOnFinishedListener listener);

    interface getPullListOnFinishedListener{
        void onFinishedList(List<PullRequest> list);
    }

    void getPullRequestList(getPullListOnFinishedListener listener, String repoName,
                            String ownerName);
}
