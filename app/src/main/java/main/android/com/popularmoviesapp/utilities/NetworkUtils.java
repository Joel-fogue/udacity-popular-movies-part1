package main.android.com.popularmoviesapp.utilities;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import main.android.com.popularmoviesapp.BuildConfig;
import main.android.com.popularmoviesapp.MainActivity;
import main.android.com.popularmoviesapp.R;

public class NetworkUtils {

    public static final String TMDB_BASE_URL = MainActivity.getContext().getString(R.string.base_url);
    public static final String QUERY_PARAM=MainActivity.getContext().getString(R.string.api_key);
    //public static final String API_KEY="INSERT_API_KEY_HERE";
    //public static final String API_KEY="3845e129e7a3c2d4c50bbf74d58550d8";
    /**
     * Builds the URL used to query GitHub.
     *
     * @return The URL to use to query the GitHub server.
     */
    public static URL buildUrl(String popularOrTopRatedUrl) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL+popularOrTopRatedUrl).buildUpon()
                .appendQueryParameter(QUERY_PARAM, BuildConfig.API_KEY_CONFIG)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildPosterPathUrl(String posterPath) {
        Uri builtUri = Uri.parse(MainActivity.getContext().getString(R.string.api_authority)).buildUpon()
                .appendEncodedPath(MainActivity.getContext().getString(R.string.w185))
                .appendEncodedPath(posterPath)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static JSONObject getResponseFromHttpUrl(URL url) throws Exception {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                String json = scanner.next();
                return new JSONObject(json);
            } else {
                return null;
            }
        } catch(Exception e){
            throw e;
        }
        finally {
            urlConnection.disconnect();
        }
    }



}
