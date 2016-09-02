package com.challenge.concrete.concretegithubrepolist.data.webservice;

import com.challenge.concrete.concretegithubrepolist.data.model.Model;
import com.challenge.concrete.concretegithubrepolist.data.model.PullRequest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by eliete on 8/25/16.
 */
public interface GitHubApi {

    @GET("/search/repositories")
    Call<Model> getRepositoryList(@QueryMap Map<String, String> query);

    @GET("repos/{owner}/{repository}/pulls")
    Call<List<PullRequest>> getPullRequestList(@Path("owner") String owner,
                                              @Path("repository") String repo);

}
