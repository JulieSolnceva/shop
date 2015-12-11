package julie.study;


import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AssertionFailedError;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import study.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.util.TreeIterables.breadthFirstViewTraversal;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SectionItemActivityTest {
    private static final String VALID_PHONE_NUMBER = "123-345-6789";

    private static String SECTION_1 = "тетради в клетку";
    private static String SECTION_2 = "тетради в линейку";

    final int[] sectionListCount = new int[1];
    /**
     * A JUnit {@link Rule @Rule} to init and release Espresso Intents before and after each
     * test run.
     * <p>
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link Before @Before} method.
     * <p>
     * This rule is based on {@link ActivityTestRule} and will create and launch of the activity
     * for you and also expose the activity under test.
     */
    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(
            MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.


        //возвращает объект, который может обработать Intent respondWith(new ActivityResult(Activity.RESULT_OK, null));
        intending(not(isInternal())).respondWith(new ActivityResult(Activity.RESULT_OK, null));

    }

    @Test
    public void start() {

//создаю объект intent, который передаёт в SectionItemActivityActivity MainActivity
        Intent intent = new Intent();
        intent.putExtra("sectionId", 4);


        // эмуляция передачи объекта intent из MainActivity в SectionItemActivityActivity
        intending(hasComponent(hasShortClassName(".SectionItemActivity")))
                .respondWith(new ActivityResult(MainActivity.RESULT_OK, intent));



        getSectionListCount();
        if(sectionListCount[0]!=2)  throw new AssertionFailedError("Неверное количество подразделов");


        onView(withId(R.id.goods)).check(has(2, isAssignableFrom(ImageView.class)));

       // onRow(SECTION_1);
        //onRow(SECTION_2);

    }


    private void getSectionListCount() {

        onView(withId(R.id.sections)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                sectionListCount[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));
    }

    /**
     * Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific row.
     * <p>
     * Note: A custom matcher can be used to match the content and have more readable code.
     * See the Custom Matcher Sample.
     * </p>
     *
     * @param str the content of the field
     * @return a {@link DataInteraction} referencing the row
     */

   /* private static DataInteraction onRow(String str) {
        return onData(hasEntry(equalTo(LongListActivity.ROW_TEXT), is(str)));
    }*/



    /**
     * Sugar for has(int, isAssignableFrom(Class)).
     *
     *  Example: onView(rootView).check(has(3, EditText.class);
     */


    /**
     *
     *   public static ViewAssertion has(final int expectedCount, Class<? extends View> clazz) {
     return has(expectedCount, isAssignableFrom(clazz));
     }
     * Returns a generic {@link ViewAssertion} that asserts that there is a
     * given number of descendant views that match the specified matcher.
     *
     * Example: onView(rootView).check(has(3, isAssignableFrom(EditText.class));
     *
     * @param expectedCount the number of descendant views that should match the specified matcher
     * @param selector the matcher to select the descendant views
     * @throws AssertionError if the number of views that match the selector is different from expectedCount
     */
    public static ViewAssertion has(final int expectedCount, final Matcher<View> selector) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
               // checkArgument(view.isPresent());
                View rootView = view;

                Iterable<View> descendantViews = breadthFirstViewTraversal(rootView);
                List<View> selectedViews = new ArrayList<View>();
                for (View descendantView : descendantViews) {
                    if (selector.matches(descendantView)) {
                        selectedViews.add(descendantView);
                    }
                }

                if (selectedViews.size() != expectedCount) {
                    String errorMessage = HumanReadables.getViewHierarchyErrorMessage(rootView,
                            selectedViews,
                            String.format("Found %d views instead of %d matching: %s", selectedViews.size(), expectedCount, selector),
                            "****MATCHES****");
                    throw new AssertionFailedError(errorMessage);
                }
            }
        };
    }
}
