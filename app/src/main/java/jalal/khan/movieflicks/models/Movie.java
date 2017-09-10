package jalal.khan.movieflicks.models;

import android.media.Image;
import android.media.midi.MidiOutputPort;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jalal on 9/9/2017.
 */

public class Movie {

    public enum MovieType {
        POPULAR, BASIC
    }

    private String id;
    private String title;
    private String description;
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;
    private Image poster;
    private Image backdrop;

    @SerializedName("vote_average")
    private double voteAverage;

    public MovieType movieType;

    public Movie(String title, String description, Image poster, Image backdrop) {
        this.title = title;
        this.description = description;
        this.poster = poster;
        this.backdrop = backdrop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getPoster() {
        return poster;
    }

    public void setPoster(Image poster) {
        this.poster = poster;
    }

    public Image getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(Image backdrop) {
        this.backdrop = backdrop;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public MovieType getMovieType() {
        if(voteAverage > 5.0) {
            movieType = MovieType.POPULAR;
        }
        else {
            movieType = MovieType.BASIC;
        }
        return movieType;
    }

    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }
}
