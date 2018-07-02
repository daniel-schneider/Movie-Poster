package com.popular.movies.popularmovies.utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.popular.movies.popularmovies.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by danielschneider on 5/5/18.
 */

public class NetworkUtilities extends ContextWrapper {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String POPULAR_PATH = "/popular?api_key=";
    private static final String HIGHEST_RATED = "/top_rated?api_key=";
    private static final String LANGUAGE_PAGE = "&language=en-US&page=1";
    private static final String API_KEY = BuildConfig.API_KEY;

    ProgressBar bar;

    public void setProgressBar(ProgressBar bar) {
        this.bar = bar;
    }

    public NetworkUtilities (Context base) {
        super(base);
    }

    public static String getPopularMovies() {

        String movieData = null;

        try {
            movieData = new JsonTask().execute(BASE_URL + POPULAR_PATH + API_KEY + LANGUAGE_PAGE).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return movieData;

    }

    public static String getHighestRatedMovies() {

        String movieData = null;

        try {
            movieData = new JsonTask().execute(BASE_URL + HIGHEST_RATED + API_KEY + LANGUAGE_PAGE).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return movieData;

    }

    public static String getMovieTrailors(String id) {
        String trailorData = null;

        try {
            trailorData = new JsonTask().execute(BASE_URL + "/" + id + "/videos?api_key=" + API_KEY + "&language=en-US").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return trailorData;
    }

    public static String getMovieReview(String id) {
        String reviewData = null;

        try {
            reviewData = new JsonTask().execute(BASE_URL + "/" + id + "/reviews?api_key=" + API_KEY + "&language=en-US").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return reviewData;
    }

    public static void getMovieReviews() {

    }

    private static class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();


//            pd = new ProgressDialog(MainActivity.this);
//            pd.setMessage("Please wait");
//            pd.setCancelable(false);
//            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            if (pd.isShowing()){
//                pd.dismiss();
//            }
//            txtJson.setText(result);
        }
    }

    public static boolean isDeviceConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }


}
