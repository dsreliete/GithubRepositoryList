package com.challenge.concrete.concretegithubrepolist.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eliete on 8/25/16.
 */
public class Repository {

    public String name;
    public String description;

    @SerializedName("stargazers_count")
    public int starCount;

    @SerializedName("fork_count")
    public int forkCount;

    @SerializedName("owner")
    public Owner owner;

    public Repository(String name, String description, int starCount, int forkCount, Owner owner) {
        this.name = name;
        this.description = description;
        this.starCount = starCount;
        this.forkCount = forkCount;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", starCount=" + starCount +
                ", forkCount=" + forkCount +
                ", owner=" + owner +
                '}';
    }
}
