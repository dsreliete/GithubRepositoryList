package com.challenge.concrete.concretegithubrepolist.pullRequests;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.challenge.concrete.concretegithubrepolist.R;
import com.challenge.concrete.concretegithubrepolist.data.model.PullRequest;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eliete on 8/29/16.
 */
public class PullRequestAdapter extends RecyclerView.Adapter<PullRequestAdapter.ViewHolder> {

    Context context;
    List<PullRequest> pullList;
    PullRequestActivity.PullRequestTouchListener pullRequestTouchListener;

    public PullRequestAdapter(List<PullRequest> list,
                              PullRequestActivity.PullRequestTouchListener listener) {
        pullList = list;
        pullRequestTouchListener = listener;
    }

    @Override
    public PullRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_pull_item, parent, false);
        return new ViewHolder(view, pullRequestTouchListener);
    }

    @Override
    public void onBindViewHolder(PullRequestAdapter.ViewHolder holder, int position) {
        PullRequest pullRequest = getItem(position);
        if (pullRequest != null) {
            holder.pullTitle.setText(pullRequest.title);
            holder.pullDescription.setText(pullRequest.body);
            holder.userName.setText(pullRequest.owner.name);

            Picasso.with(context)
                    .load(pullRequest.owner.avatar)
                    .placeholder(R.drawable.ic_account)
                    .into(holder.avatar);

        }
    }

    @Override
    public int getItemCount() {
        return pullList.size();
    }

    private PullRequest getItem(int position) {
        return pullList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.repo_name)
        TextView pullTitle;
        @BindView(R.id.repo_description)
        TextView pullDescription;
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.user_name)
        TextView userName;

        public ViewHolder(View view, PullRequestActivity.PullRequestTouchListener listener) {
            super(view);
            pullRequestTouchListener = listener;
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            PullRequest pullRequest = getItem(position);
            pullRequestTouchListener.onPullClick(pullRequest);

        }
    }

}
