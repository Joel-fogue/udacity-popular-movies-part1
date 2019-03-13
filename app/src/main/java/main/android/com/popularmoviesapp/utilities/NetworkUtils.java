package main.android.com.popularmoviesapp.utilities;

import android.net.Uri;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/popular";
    public static final String QUERY_PARAM="api_key";
    public static final String API_KEY="3845e129e7a3c2d4c50bbf74d58550d8";
    /**
     * Builds the URL used to query GitHub.
     *
     * @return The URL to use to query the GitHub server.
     */
    public static URL buildUrl() {
        Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                //.appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    //w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    public static URL buildPosterPathUrl(String posterPath) {
        Uri builtUri = Uri.parse("https://image.tmdb.org/t/p/").buildUpon()
                .appendPath("/w185/")
                .appendPath(posterPath)
                //.appendQueryParameter(PARAM_SORT, sortBy)
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
