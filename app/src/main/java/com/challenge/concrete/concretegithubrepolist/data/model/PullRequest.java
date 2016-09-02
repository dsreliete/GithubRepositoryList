package com.challenge.concrete.concretegithubrepolist.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eliete on 8/25/16.
 */
public class PullRequest {

    public String title;
    public String body;

    @SerializedName("user")
    public Owner owner;

    @SerializedName("html_url")
    public String pullRequestLink;

    public PullRequest(String title, String body, Owner owner, String pullRequestLink) {
        this.title = title;
        this.body = body;
        this.owner = owner;
        this.pullRequestLink = pullRequestLink;
    }

    @Override
    public String toString() {
        return "PullRequest{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", owner=" + owner +
                ", pullRequestLink='" + pullRequestLink + '\'' +
                '}';
    }
}
