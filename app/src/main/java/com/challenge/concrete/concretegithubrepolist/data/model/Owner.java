package com.challenge.concrete.concretegithubrepolist.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eliete on 8/25/16.
 */
public class Owner {

    @SerializedName("login")
    public String name;

    @SerializedName("avatar_url")
    public String avatar;

    public Owner(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
