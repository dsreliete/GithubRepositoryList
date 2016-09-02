package com.challenge.concrete.concretegithubrepolist.githubRepository;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.challenge.concrete.concretegithubrepolist.ColorTransformation;
import com.challenge.concrete.concretegithubrepolist.R;
import com.challenge.concrete.concretegithubrepolist.data.model.Repository;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eliete on 8/28/16.
 */
public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private List<Repository> repoList;
    private MainActivity.RepoTouchListener touchListener;
    private Context context;

    public RepoAdapter(List<Repository> list, MainActivity.RepoTouchListener repoTouchListener) {
        repoList = list;
        touchListener = repoTouchListener;
    }

    @Override
    public RepoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycler_repo_item, parent, false);
            return new ViewHolder(view, touchListener);
    }

    @Override
    public void onBindViewHolder(RepoAdapter.ViewHolder holder, int position) {
            Repository repo = getItem(position);
            if (repo != null){
                holder.repoName.setText(repo.name);
                holder.repoDescription.setText(repo.description);
                holder.forkCount.setText(String.valueOf(repo.forkCount));
                holder.starCount.setText(String.valueOf(repo.starCount));
                holder.userName.setText(repo.owner.name);

                Picasso.with(context)
                        .load(repo.owner.avatar)
                        .placeholder(R.drawable.ic_account)
                        .into(holder.avatar);
            }

    }

    public Repository getItem(int position){
        return repoList.get(position);
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.repo_name)
        TextView repoName;
        @BindView(R.id.repo_description)
        TextView repoDescription;
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.fork_count)
        TextView forkCount;
        @BindView(R.id.star_count)
        TextView starCount;
        @BindView(R.id.fork)
        ImageView fork;
        @BindView(R.id.star)
        ImageView star;

        private MainActivity.RepoTouchListener touchListener;


        public ViewHolder(View view, MainActivity.RepoTouchListener repoTouchListener) {
            super(view);
            touchListener = repoTouchListener;
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);

            Picasso.with(context)
                    .load( R.drawable.ic_star )
                    .transform(new ColorTransformation(context.getResources().getColor( R.color.colorAccent)))
                    .into(fork);

            Picasso.with(context)
                    .load( R.drawable.ic_fork )
                    .transform(new ColorTransformation(context.getResources().getColor(R.color.colorAccent)))
                    .into(star);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Repository repository = getItem(position);
            touchListener.onRepoTouch(repository);
        }
    }

}
