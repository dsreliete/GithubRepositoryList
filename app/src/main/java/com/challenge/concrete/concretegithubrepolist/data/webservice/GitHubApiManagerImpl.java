package com.challenge.concrete.concretegithubrepolist.data.webservice;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eliete on 8/25/16.
 */
public class GitHubApiManagerImpl implements GitHubApiManager {


    public static final String BASE_URL = "https://api.github.com/";
    private GitHubApi gitHubApi;

    public GitHubApiManagerImpl() {
        setupGitHubApi();
    }

    @Override
    public GitHubApi getGitHubApiInstance() {
        return gitHubApi;
    }

    @Override
    public void setupGitHubApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHTTPClient = new OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHTTPClient)
                .build();

        gitHubApi = retrofit.create(GitHubApi.class);
    }
}
