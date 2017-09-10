package jalal.khan.movieflicks.http;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import jalal.khan.movieflicks.MainActivity;

import static junit.framework.Assert.assertTrue;


/**
 * Created by Jalal on 9/9/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MovieApiTest {

    @Rule
    public ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGetMovies() throws Exception {
        Context activity = InstrumentationRegistry.getTargetContext();
        MovieApi api = new MovieApi(activity);
        api.getMovies(new MovieJsonResponse() {
            @Override
            public void respond(boolean success, JSONObject response) {
                assertTrue(response.has("results"));
                try {
                    JSONArray results = response.getJSONArray("results");
                    assertTrue(results.length() > 0);
                    assertTrue(results.getJSONObject(0).has("title"));
                    assertTrue(results.getJSONObject(0).has("description"));
                    assertTrue(results.getJSONObject(0).has("poster_path"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}