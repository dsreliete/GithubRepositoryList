package com.challenge.concrete.concretegithubrepolist.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eliete on 8/26/16.
 */
public class Model {

    @SerializedName("items")
    public List<Repository> repositoryList = new ArrayList<>();
}
