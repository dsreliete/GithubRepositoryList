package com.challenge.concrete.concretegithubrepolist;

import com.challenge.concrete.concretegithubrepolist.data.DataRepository;
import com.challenge.concrete.concretegithubrepolist.data.model.Owner;
import com.challenge.concrete.concretegithubrepolist.data.model.Repository;
import com.challenge.concrete.concretegithubrepolist.githubRepository.MainActivity;
import com.challenge.concrete.concretegithubrepolist.githubRepository.MainPresenter;

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
public class MainActivityTest {

    @Mock
    DataRepository repository;

    @Mock
    MainActivity mainActivity;

    @Captor
    ArgumentCaptor<DataRepository.getRepoListOnFinishedListener>
            getListOnFinishedListenerArgumentCaptor;

    @Mock
    MainPresenter mainPresenter;

    private static List<Repository> repositoryList = Arrays.asList(
            new Repository("name", "description", 12, 25, new Owner("owner", "http:www.com.br")),
            new Repository("name", "description", 25, 75, new Owner("owner", "http:www.com.br")),
            new Repository("name", "description", 29, 37, new Owner("owner", "http:www.com.br")));

    @Before
    public void setupUserPresenter(){
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(mainActivity, repository);
    }

    @Test
    public void touchOnItem_shouldShowPullRequestActivity() {
        Repository repo = new Repository("name", "description",12, 25, new Owner("owner",
                "http:www.com.br"));
        mainPresenter.openRepoPullRequests(repo);
        Mockito.verify(mainActivity).showPullRequestActivity(Mockito.any(Repository.class));
    }

    @Test
    public void loadRepositoryFromDataRepository_shouldLoadIntoView(){
        mainPresenter.fetchRepoList(1);
        Mockito.verify(repository).
                getRepositoryList(eq(1), getListOnFinishedListenerArgumentCaptor.capture());
        getListOnFinishedListenerArgumentCaptor.getValue().onFinishedList(repositoryList);
        Mockito.verify(mainActivity).hideProgress();
        Mockito.verify(mainActivity).showRepoList(repositoryList);
    }


    @Test
    public void failLoadRepositoryFromDataRepository_shouldLoadErrorMessage(){
        mainPresenter.fetchRepoList(1);
        Mockito.verify(repository).
                getRepositoryList(eq(1), getListOnFinishedListenerArgumentCaptor.capture());
        getListOnFinishedListenerArgumentCaptor.getValue().onFinishedList(null);
        Mockito.verify(mainActivity).showNoRepoText();
    }

}
