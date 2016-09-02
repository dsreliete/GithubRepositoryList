package com.challenge.concrete.concretegithubrepolist.pullRequests;

import com.challenge.concrete.concretegithubrepolist.data.DataRepository;
import com.challenge.concrete.concretegithubrepolist.data.model.PullRequest;

import java.util.List;

/**
 * Created by eliete on 8/29/16.
 */
public class PullRequestPresenter implements PullRequestContract.UserActionListener,
                            DataRepository.getPullListOnFinishedListener{

    private PullRequestContract.View pullRequestContract;
    private DataRepository dataRepository;

    public PullRequestPresenter(PullRequestActivity pullRequestActivity, DataRepository repository) {
        pullRequestContract = pullRequestActivity;
        dataRepository = repository;
    }

    @Override
    public void fetchPullList(String repoName, String ownerName) {
        if (pullRequestContract != null){
            pullRequestContract.showProgress();
        }
        dataRepository.getPullRequestList(this, repoName, ownerName);
    }

    @Override
    public void onFinishedList(List<PullRequest> list) {
        if (list != null){
            pullRequestContract.hideProgress();
            pullRequestContract.showPullList(list);
        }else{
            pullRequestContract.showNoPullText();
        }
    }
}
