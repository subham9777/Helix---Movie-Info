package android.subham.moviesearch.Activities;

import android.os.Bundle;
import android.subham.moviesearch.Model.Movie;
import android.subham.moviesearch.R;
import android.subham.moviesearch.Util.Constants;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;
    private RequestQueue queue;
    private ImageView poster;
    private TextView movieTitle;
    private TextView movieReleased;
    private TextView movieCategory;
    private TextView movieRating;
    private TextView movieRuntime;
    private TextView movieDirector;
    private TextView movieActors;
    private TextView movieWriter;
    private TextView movieBoxOff;
    private String movieId;
    private TextView moviePlot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        queue = Volley.newRequestQueue(this);
        movie = (Movie) getIntent().getSerializableExtra("movieabc");
        movieId = movie.getImdbId();
        Log.d("ID:", movieId);
        setUI();
        getMovieDetails(movieId);
    }

    private void getMovieDetails(final String Id) {
        final JsonObjectRequest movieDetails = new JsonObjectRequest(Request.Method.GET, Constants.URL_LEFT + Constants.URL_T + Id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("URl : ", Constants.URL_LEFT + Constants.URL_T + Id);
                try {
                    if (response.has("Ratings")) {

                        JSONArray rating = response.getJSONArray("Ratings");

                        String source = null;
                        String value = null;
                        if (rating.length() > 0) {

                            JSONObject mRatings = rating.getJSONObject(rating.length() - 1);
                            source = mRatings.getString("Source");
                            value = mRatings.getString("Value");
                            movieRating.setText(source + " : " + value);

                        } else {
                            movieRating.setText("Ratings : N/A");
                        }
                        movieTitle.setText(response.getString("Title"));
                        movieReleased.setText("Released : " + response.getString("Released"));
                        movieCategory.setText("Type : " + response.getString("Type"));
                        movieRuntime.setText("Run-time : " + response.getString("Runtime"));
                        movieDirector.setText("Director : " + response.getString("Director"));
                        movieActors.setText("Actors : " + response.getString("Actors"));
                        movieWriter.setText("Writer : " + response.getString("Writer"));
                        moviePlot.setText("Plot : " + response.getString("Plot"));
                        Picasso.with(getApplicationContext()).load(response.getString("Poster")).into(poster);
                        movieBoxOff.setText("Box Office : " + response.getString("BoxOffice"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error : ", error.getMessage());

            }
        });
        queue.add(movieDetails);
    }

    private void setUI() {
        poster = findViewById(R.id.movieImageDet);
        movieTitle = findViewById(R.id.movieTitleDet);
        movieReleased = findViewById(R.id.movieReleaseDets);
        movieCategory = findViewById(R.id.movieCatDet);
        movieRating = findViewById(R.id.movieRatingDet);
        movieRuntime = findViewById(R.id.movieRuntimeDet);
        movieDirector = findViewById(R.id.directedByDet);
        movieActors = findViewById(R.id.actorsDet);
        movieWriter = findViewById(R.id.writersDet);
        movieBoxOff = findViewById(R.id.boxOfficeDet);
        moviePlot = findViewById(R.id.plotDet);
    }


}
