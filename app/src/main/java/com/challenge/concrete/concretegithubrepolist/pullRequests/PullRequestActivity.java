package com.challenge.concrete.concretegithubrepolist.pullRequests;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.challenge.concrete.concretegithubrepolist.R;
import com.challenge.concrete.concretegithubrepolist.data.DataRepositoryImpl;
import com.challenge.concrete.concretegithubrepolist.data.model.PullRequest;
import com.challenge.concrete.concretegithubrepolist.githubRepository.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eliete on 8/25/16.
 */
public class PullRequestActivity extends AppCompatActivity implements PullRequestContract.View{

    public static final String TAG = PullRequest.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_item)
    TextView noPullText;

    private LinearLayoutManager layoutManager;
    private int lastVisiblePosition;
    private PullRequestAdapter pullAdapter;
    private List<PullRequest> pullList = new ArrayList<>();
    private PullRequestContract.UserActionListener userActionListener;
    private String repoName;
    private String ownerName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullrequest);
        ButterKnife.bind(this);

        if (getIntent() != null){
            repoName = getIntent().getStringExtra(MainActivity.EXTRA_REPOSITORY);
            ownerName = getIntent().getStringExtra(MainActivity.EXTRA_OWNER);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(repoName);
        userActionListener = new PullRequestPresenter(this, new DataRepositoryImpl());
        userActionListener.fetchPullList(repoName, ownerName);
        setupRecyclerView(lastVisiblePosition);
    }

    private void setupRecyclerView(int lastPosition) {
        pullAdapter = new PullRequestAdapter(pullList, pullTouchListener);
        recyclerView.setAdapter(pullAdapter);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(lastPosition);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPullList(List<PullRequest> list) {
        pullList.addAll(list);
        pullAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoPullText() {
        recyclerView.setVisibility(View.GONE);
        noPullText.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MainActivity.EXTRA_POSITION, layoutManager.findLastVisibleItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastVisiblePosition = savedInstanceState.getInt(MainActivity.EXTRA_POSITION);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setupRecyclerView(layoutManager.findLastVisibleItemPosition());
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setupRecyclerView(layoutManager.findLastVisibleItemPosition());
        }
    }

    PullRequestTouchListener pullTouchListener = new PullRequestTouchListener(){
        @Override
        public void onPullClick(PullRequest clickedPull) {
            if (clickedPull != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(clickedPull.pullRequestLink));
                startActivity(intent);
            }
        }
    };

    public interface PullRequestTouchListener {
        void onPullClick(PullRequest clickedPull);
    }
}
