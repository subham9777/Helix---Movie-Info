package android.subham.moviesearch.Model;

import java.io.Serializable;

/**
 * Created by Subham on 05-03-2018.
 */

public class Movie implements Serializable {
    private String poster;
    private String title;
    private String type;
    private String year;
    private String imdbId;

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
    public String getImdbId() {
        return imdbId;
    }

    public String getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}
