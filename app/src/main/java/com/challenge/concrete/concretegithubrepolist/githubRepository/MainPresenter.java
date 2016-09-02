package com.challenge.concrete.concretegithubrepolist.githubRepository;

import android.support.annotation.NonNull;

import com.challenge.concrete.concretegithubrepolist.data.DataRepository;
import com.challenge.concrete.concretegithubrepolist.data.model.Repository;

import java.util.List;

/**
 * Created by eliete on 8/26/16.
 */
public class MainPresenter implements MainContract.UserActionListener,
        DataRepository.getRepoListOnFinishedListener {

    private MainContract.View mainContract;
    private DataRepository dataRepository;

    public MainPresenter(MainActivity mainActivity, DataRepository repository) {
        mainContract = mainActivity;
        dataRepository = repository;
    }

    @Override
    public void fetchRepoList(int page) {
        if (mainContract != null) {
            mainContract.showProgress();
        }
        dataRepository.getRepositoryList(page, this);

    }

    @Override
    public void openRepoPullRequests(@NonNull Repository requestedRepository) {
        mainContract.showPullRequestActivity(requestedRepository);
    }

    @Override
    public void onFinishedList(List<Repository> list) {
        if (list!= null) {
            mainContract.hideProgress();
            mainContract.showRepoList(list);
        }else{
            mainContract.showNoRepoText();
        }
    }
}
