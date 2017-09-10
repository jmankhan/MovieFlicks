package jalal.khan.movieflicks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import jalal.khan.movieflicks.http.MovieApi;
import jalal.khan.movieflicks.http.MovieJsonResponse;
import jalal.khan.movieflicks.models.Movie;
import jalal.khan.movieflicks.movieadapter.MovieListAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView movieListView;
    private List<Movie> movieList = new ArrayList<>();
    private MovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieListView = (ListView) findViewById(R.id.movieListView);
        adapter = new MovieListAdapter(MainActivity.this, movieList);
        movieListView.setAdapter(adapter);

        MovieApi api = new MovieApi(this);
        api.getMovies(new MovieJsonResponse() {
            @Override
            public void respond(boolean success, final JSONObject response) {
                if(success) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Movie movie = new Gson().fromJson(String.valueOf(response), Movie.class);
                            adapter.add(movie);
                        }
                    });
                }
                else {
                    Log.d("movie_api", "error " + String.valueOf(response));
                }
            }
        });

    }
}
