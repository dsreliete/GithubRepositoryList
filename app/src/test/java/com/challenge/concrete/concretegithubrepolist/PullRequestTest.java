package com.challenge.concrete.concretegithubrepolist;

import com.challenge.concrete.concretegithubrepolist.data.DataRepository;
import com.challenge.concrete.concretegithubrepolist.data.model.Owner;
import com.challenge.concrete.concretegithubrepolist.data.model.PullRequest;
import com.challenge.concrete.concretegithubrepolist.pullRequests.PullRequestActivity;
import com.challenge.concrete.concretegithubrepolist.pullRequests.PullRequestPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.eq;
/**
 * Created by eliete on 8/29/16.
 */
public class PullRequestTest {

    @Mock
    DataRepository repository;

    @Mock
    PullRequestActivity pullRequestActivity;

    @Captor
    ArgumentCaptor<DataRepository.getPullListOnFinishedListener>
            getListOnFinishedListenerArgumentCaptor;

    @Mock
    PullRequestPresenter pullPresenter;

    private static List<PullRequest> pullRequestList = Arrays.asList(
            new PullRequest("title", "body", new Owner("name", "avatarUrl"), "http://wwww.com.br"),
            new PullRequest("title", "body", new Owner("name", "avatarUrl"), "http://wwww.com.br"),
            new PullRequest("title", "body", new Owner("name", "avatarUrl"), "http://wwww.com.br"));

    @Before
    public void setupUserPresenter(){
        MockitoAnnotations.initMocks(this);
        pullPresenter = new PullRequestPresenter(pullRequestActivity, repository);
    }

    @Test
    public void fetchPullRequest_shouldLoadProgresIntoView(){
        pullPresenter.fetchPullList("repoName", "ownerName");
        Mockito.verify(pullRequestActivity).showProgress();
    }

    @Test
    public void loadPullRequestFromDataRepository_shouldLoadIntoView(){
        pullPresenter.fetchPullList("repoName", "ownerName");
        Mockito.verify(repository).
                getPullRequestList(getListOnFinishedListenerArgumentCaptor.capture(), eq("repoName"),
                        eq("ownerName"));
        getListOnFinishedListenerArgumentCaptor.getValue().onFinishedList(pullRequestList);
        Mockito.verify(pullRequestActivity).hideProgress();
        Mockito.verify(pullRequestActivity).showPullList(pullRequestList);
    }


    @Test
    public void failLoadPullRequestFromDataRepository_shouldLoadErrorMessageIntoView(){
        pullPresenter.fetchPullList("repoName", "ownerName");
        Mockito.verify(repository).
                getPullRequestList(getListOnFinishedListenerArgumentCaptor.capture(), eq("repoName"),
                        eq("ownerName"));
        getListOnFinishedListenerArgumentCaptor.getValue().onFinishedList(null);
        Mockito.verify(pullRequestActivity).showNoPullText();
    }

}
