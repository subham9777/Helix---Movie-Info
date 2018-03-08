package android.subham.moviesearch.Activities;

import android.os.Bundle;
import android.subham.moviesearch.Data.MovieRecyclerViewAdapter;
import android.subham.moviesearch.Model.Movie;
import android.subham.moviesearch.R;
import android.subham.moviesearch.Util.Constants;
import android.subham.moviesearch.Util.Prefs;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private List<Movie> movieList;
    private RequestQueue queue;
    private AlertDialog.Builder alertDialog;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        Prefs prefs = new Prefs(this);
        String search = prefs.getSearch();
        Log.d("Search",search);

        recyclerView = (RecyclerView)  findViewById(R.id.recView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieList = new ArrayList<>();
//        getMovie(search);
        movieList = getMovie(search);
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this,movieList);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        movieRecyclerViewAdapter.notifyDataSetChanged();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }

    public List<Movie> getMovie(final String searchTerm) {
        movieList.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL_LEFT +Constants.URL_S+ searchTerm, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d("URL",Constants.URL_LEFT+searchTerm);
                    JSONArray moviesArray = response.getJSONArray("Search");
                    for (int i = 0; i < moviesArray.length(); i++) {
                        JSONObject moviesObj = moviesArray.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setTitle(moviesObj.getString("Title"));
                        movie.setYear("Year Released : " + moviesObj.getString("Year"));
                        movie.setType("Type : " + moviesObj.getString("Type"));
                        movie.setPoster(moviesObj.getString("Poster"));
                        movie.setImdbId(moviesObj.getString("imdbID"));
                        movieList.add(movie);
//                        Log.d("Movies :", movie.getTitle());

                    }
                    movieRecyclerViewAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
        return movieList;

    }

    public void showInputDialog()
    {
        alertDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.movie_search,null);
        final EditText editText = view.findViewById(R.id.search_mov);
        Button button = view.findViewById(R.id.mov_button);

        alertDialog.setView(view);
        dialog = alertDialog.create();
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs prefs = new Prefs(MainActivity.this);
                if (!editText.getText().toString().isEmpty())
                {
                    String search = editText.getText().toString();
                    prefs.setSearch(search);
                    movieList.clear();
                    getMovie(search);
                    movieRecyclerViewAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

            }
        });
    }
}
