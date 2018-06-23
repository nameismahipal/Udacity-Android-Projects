package www.androidcitizen.com.popularmoviesone.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import www.androidcitizen.com.popularmoviesone.config.BaseConfig;

/**
 * Re-used from Udacity, Android Developer Course, Lesson T02.05
 */

public class NetworkUtils {

    public static URL buildUrl(String searchQuery, int pageNum) {
        Uri builtUri = Uri.parse(searchQuery).buildUpon()
                .appendQueryParameter(BaseConfig.apiKey, BaseConfig.API_KEY)
                .appendQueryParameter("page", Integer.toString(pageNum))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
