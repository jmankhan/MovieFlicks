package jalal.khan.movieflicks.http;

import org.json.JSONObject;

/**
 * Created by Jalal on 9/10/2017.
 */

public interface MovieJsonResponse {
    void respond(boolean success, JSONObject response);

}
