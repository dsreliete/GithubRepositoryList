package com.challenge.concrete.concretegithubrepolist.githubRepository;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.challenge.concrete.concretegithubrepolist.AlertDialogFragment;
import com.challenge.concrete.concretegithubrepolist.EndlessRecyclerViewScrollListener;
import com.challenge.concrete.concretegithubrepolist.R;
import com.challenge.concrete.concretegithubrepolist.data.DataRepositoryImpl;
import com.challenge.concrete.concretegithubrepolist.data.model.Repository;
import com.challenge.concrete.concretegithubrepolist.pullRequests.PullRequestActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends InternetDetectionActivity implements MainContract.View {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_PAGE = "page";
    public static final String EXTRA_REPOSITORY = "repository";
    public static final String EXTRA_OWNER = "owner";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_item)
    TextView noRepoText;

    private MainContract.UserActionListener userActionListener;
    private List<Repository> repoList = new ArrayList<>();
    private RepoAdapter adapter;
    private LinearLayoutManager layoutManager;
    private int currentPage = 1;
    private int lastVisiblePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        userActionListener = new MainPresenter(this, new DataRepositoryImpl());

            if (MainActivity.hasConnection(this)) {
                userActionListener.fetchRepoList(currentPage);
            } else {
                AlertDialogFragment alertDialogFragment =
                        AlertDialogFragment.newInstance(getResources().getString(R.string.network_msg));
                alertDialogFragment.show(getSupportFragmentManager(), "alert");
            }
        setupRecyclerView(lastVisiblePosition);

    }

    private void setupRecyclerView(int lastPosition) {
        adapter = new RepoAdapter(repoList, repoTouchListener);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(lastPosition);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page) {
                currentPage = page;
                userActionListener.fetchRepoList(currentPage);
            }
        });
    }


    public static boolean hasConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null){
            return networkInfo.isConnected();
        }else{
            return false;
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRepoList(List<Repository> list) {
        repoList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoRepoText() {
        recyclerView.setVisibility(View.GONE);
        noRepoText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPullRequestActivity(Repository repo) {
        if (repo != null) {
            Intent intent = new Intent(this, PullRequestActivity.class);
            intent.putExtra(EXTRA_OWNER, repo.owner.name);
            intent.putExtra(EXTRA_REPOSITORY, repo.name);
            startActivity(intent);
        }
    }

    @Override
    public void setupDownload() {
        userActionListener = new MainPresenter(this, new DataRepositoryImpl());
        userActionListener.fetchRepoList(1);
    }

    public interface RepoTouchListener {
        void onRepoTouch(Repository touchedRepo);
    }

    RepoTouchListener repoTouchListener = new RepoTouchListener() {
        @Override
        public void onRepoTouch(Repository touchedRepo) {
            userActionListener.openRepoPullRequests(touchedRepo);
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MainActivity.EXTRA_POSITION, layoutManager.findLastVisibleItemPosition());
        outState.putInt(MainActivity.EXTRA_PAGE, currentPage);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastVisiblePosition = savedInstanceState.getInt(MainActivity.EXTRA_POSITION);
        currentPage = savedInstanceState.getInt(MainActivity.EXTRA_PAGE);
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

}
