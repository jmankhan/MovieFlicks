package jalal.khan.movieflicks.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jalal.khan.movieflicks.R;

/**
 * Created by Jalal on 9/9/2017.
 */

public class MovieApi {


    private AsyncHttpClient asyncClient;

    private Context context;

    public MovieApi(Context context) {
        this.context = context;
        asyncClient = new AsyncHttpClient();
    }

    public void getMovies(final MovieJsonResponse callback) {
        String apiKey = context.getString(R.string.movie_api_key);
        String baseUrl = context.getString(R.string.movie_base_url);
        String discoverPath = context.getString(R.string.movie_discover_path);

        RequestParams params = new RequestParams();
        params.put("api_key", apiKey);

        JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray responses = response.getJSONArray("results");

                    for (int i = 0; i < responses.length(); i++) {
                        String id = responses.getJSONObject(i).getString("id");
                        getMovie(id, callback);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callback.respond(false, errorResponse);
            }
        };

        //need this line for tests to run
        responseHandler.setUsePoolThread(true);
        asyncClient.get(context, baseUrl + discoverPath, params, responseHandler);
    }


    private void getMovie(String id, final MovieJsonResponse callback) {
        String apiKey = context.getString(R.string.movie_api_key);
        String baseUrl = context.getString(R.string.movie_base_url);
        String moviePath = context.getString(R.string.movie_movie_path);

        RequestParams params = new RequestParams();
        params.put("api_key", apiKey);
        params.put("language", "en-US");

        JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                callback.respond(true, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callback.respond(false, errorResponse);
            }
        };
        responseHandler.setUsePoolThread(true);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(context, baseUrl + moviePath + id, params, responseHandler);
    }

    public void getImage(String id, final MovieBitmapResponse callback) {
        String url = context.getString(R.string.movie_image_base_url) + id + ".jpg";

        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(responseBody,0,responseBody.length);
                callback.respond(true, bitmap);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callback.respond(false, null);
            }
        };
        responseHandler.setUsePoolThread(true);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(context, url, responseHandler);
    }
}
