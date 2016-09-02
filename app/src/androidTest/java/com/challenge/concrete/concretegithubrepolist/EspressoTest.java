package com.challenge.concrete.concretegithubrepolist;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.challenge.concrete.concretegithubrepolist.githubRepository.MainActivity;
import com.challenge.concrete.concretegithubrepolist.pullRequests.PullRequestActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by eliete on 8/29/16.
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity>
            activityRule = new ActivityTestRule<>(MainActivity.class, false, true);

    @Test
    public void whenActivityIsLaunched_shouldDisplayInitialState() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.no_item)).check((matches(not(isDisplayed()))));
        onView(withId(R.id.progressBar)).check((matches(not(isDisplayed()))));
    }

    @Test
    public void checkRepositoryViewItem_isDisplayed(){
        onView(allOf(withId(R.id.repository), isDisplayed()));
        onView(allOf(withId(R.id.user), isDisplayed()));
        onView(allOf(withId(R.id.count_container), isDisplayed()));
    }

    @Test
    public void checkContentOfRepositoryViewItem_isDisplayed(){
        onView(allOf(withId(R.id.repo_name), hasSibling(withText("react-native")))).
                check(matches(isDisplayed()));
        onView(allOf(withId(R.id.user_name), hasSibling(withText("facebook")))).
                check(matches(isDisplayed()));

    }

    @Test
    public void whenTouchOnItemList_shouldStartPullRequestActivity_withExtra() {
        activityRule.launchActivity(new Intent());
        Intents.init();
        Matcher<Intent> matcher = allOf(
                hasComponent(PullRequestActivity.class.getName()),
                hasExtraWithKey(MainActivity.EXTRA_OWNER),
                hasExtraWithKey(MainActivity.EXTRA_REPOSITORY)
        );

        Instrumentation.ActivityResult
                result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

        intending(matcher).respondWith(result);

        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        intended(matcher);
        Intents.release();
    }

}
