package com.challenge.concrete.concretegithubrepolist.pullRequests;

import com.challenge.concrete.concretegithubrepolist.data.model.PullRequest;

import java.util.List;

/**
 * Created by eliete on 8/29/16.
 */
public class PullRequestContract {

    interface View{

        void showProgress();

        void hideProgress();

        void showPullList(List<PullRequest> list);

        void showNoPullText();

    }

    interface UserActionListener{

        void fetchPullList(String repoName, String ownerName);

    }
}
