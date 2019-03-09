package com.challenge.concrete.concretegithubrepolist.data;

import com.challenge.concrete.concretegithubrepolist.data.model.Model;
import com.challenge.concrete.concretegithubrepolist.data.model.PullRequest;
import com.challenge.concrete.concretegithubrepolist.data.model.Repository;
import com.challenge.concrete.concretegithubrepolist.data.webservice.GitHubApi;
import com.challenge.concrete.concretegithubrepolist.data.webservice.GitHubApiManager;
import com.challenge.concrete.concretegithubrepolist.data.webservice.GitHubApiManagerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eliete on 8/25/16.
 */
public class DataRepositoryImpl implements DataRepository{

    public static final String TAG = DataRepositoryImpl.class.getSimpleName();

    private List<Repository> repoList;
    private List<PullRequest> pullList;
    boolean handler;
    private GitHubApiManager manager = new GitHubApiManagerImpl();
    private GitHubApi gitHubApi = manager.getGitHubApiInstance();

    @Override
    public void getRepositoryList(int page, final getRepoListOnFinishedListener listener) {

        Map<String, String> data = new HashMap<>();
        data.put("q", "language:Java");
        data.put("sort", "stars");
        data.put("page", String.valueOf(page));

        gitHubApi.getRepositoryList(data).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()){
                    repoList = response.body().repositoryList;
                }
                listener.onFinishedList(repoList);
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                listener.onFinishedList(repoList);
            }
        });
    }

    @Override
    public void getPullRequestList(final getPullListOnFinishedListener listener, String repoName,
                                   String ownerName) {
        gitHubApi.getPullRequestList(ownerName, repoName).enqueue(new Callback<List<PullRequest>>() {
            @Override
            public void onResponse(Call<List<PullRequest>> call, Response<List<PullRequest>> response) {
                if (response.isSuccessful()) {
                    pullList = response.body();

                }
                listener.onFinishedList(pullList);
            }

            @Override
            public void onFailure(Call<List<PullRequest>>call, Throwable t) {
                listener.onFinishedList(pullList);

            }
        });

    }
}
