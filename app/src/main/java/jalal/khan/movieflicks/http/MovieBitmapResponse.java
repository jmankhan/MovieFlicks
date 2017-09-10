package jalal.khan.movieflicks.http;

import android.graphics.Bitmap;
import android.media.Image;

/**
 * Created by Jalal on 9/10/2017.
 */

public interface MovieBitmapResponse {
    void respond(boolean success, Bitmap response);
}
