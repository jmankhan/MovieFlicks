package jalal.khan.movieflicks.movieadapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jalal.khan.movieflicks.R;
import jalal.khan.movieflicks.models.Movie;

import static jalal.khan.movieflicks.models.Movie.*;


/**
 * Custom adapter to hold Movie data
 * Created by Jalal on 9/9/2017.
 */
public class MovieListAdapter extends ArrayAdapter<Movie> {

    private static class MovieViewHolder {
        TextView title;
        TextView description;
        ImageView poster;
    }

    public MovieListAdapter(@NonNull Context context, List<Movie> movies) {
        super(context, R.layout.movie_item, movies);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getMovieType().ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return MovieType.values().length;
    }

    /**
     * Gets the current view to render for the ListView
     * Uses a cached ViewHolder from the convertView tag if available, otherwise constructs it
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get data model to populate view from
        Movie movie = getItem(position);
        MovieViewHolder holder;

        if(convertView == null) {
            holder = new MovieViewHolder();
            convertView = getInflatedLayoutForType(getItemViewType(position));

            convertView.setTag(holder);

            holder.title        = (TextView)  convertView.findViewById(R.id.title);
            holder.description  = (TextView)  convertView.findViewById(R.id.description);
            holder.poster       = (ImageView) convertView.findViewById(R.id.poster);
        }
        else {
            holder = (MovieViewHolder) convertView.getTag();
        }

        String imageurl;
        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                || movie.getMovieType() == MovieType.POPULAR){
            imageurl = getContext().getString(R.string.movie_image_base_url) + movie.getBackdropPath();
        }
        else {
            imageurl = getContext().getString(R.string.movie_image_base_url) + movie.getPosterPath();
        }

        holder.title.setText(movie.getTitle());
        holder.description.setText(movie.getOverview());
        Picasso.with(getContext())
                .load(imageurl)
                .placeholder(R.drawable.ic_movie)
                .resize(300, 0)
                .into(holder.poster);

        return convertView;
    }

    /**
     * Finds the layout resource for the given type. has a default return value
     * @param itemViewType
     * @return
     */
    private View getInflatedLayoutForType(int itemViewType) {
        int layout;
        if(itemViewType == MovieType.BASIC.ordinal()) {
            layout = R.layout.movie_item;
        }
        else if(itemViewType == MovieType.POPULAR.ordinal()) {
            layout = R.layout.movie_item_popular;
        }
        else {
            layout = R.layout.movie_item;
        }

        return LayoutInflater.from(getContext()).inflate(layout, null);
    }
}
