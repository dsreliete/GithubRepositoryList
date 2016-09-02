package com.challenge.concrete.concretegithubrepolist.githubRepository;

import android.support.annotation.NonNull;

import com.challenge.concrete.concretegithubrepolist.data.model.Repository;

import java.util.List;

/**
 * Created by eliete on 8/26/16.
 */
public class MainContract {

    interface View{

        void showProgress();

        void hideProgress();

        void showRepoList(List<Repository> list);

        void showPullRequestActivity(Repository repo);

        void showNoRepoText();

    }

    interface UserActionListener{

        void fetchRepoList(int page);

        void openRepoPullRequests(@NonNull Repository requestedRepository);

    }
}
