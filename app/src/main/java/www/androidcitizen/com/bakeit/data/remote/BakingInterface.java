package www.androidcitizen.com.bakeit.data.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mahi on 01/07/18.
 * www.androidcitizen.com
 */

public class BakingInterface {

    private final static String BAKING_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static Retrofit retrofit;

    private static volatile BakingService bakingService;

    private BakingInterface() {

        //prevention from reflection api (to avoid creating another instance)
        if(null != bakingService) {
            throw new RuntimeException("Use getBakingService() to get single instance of the class");
        }
    }

    public static Retrofit getBakingInterface() {

        if(null == retrofit) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor());



            retrofit = new Retrofit.Builder()
                     .baseUrl(BAKING_BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .client(httpClient.build())
                     .build();
        }

        return retrofit;
    }

    public static BakingService getBakingService(){
        if(null == bakingService) {

            synchronized (BakingService.class) {

                if(null == bakingService) {
                    bakingService = getBakingInterface().create(BakingService.class);
                }
            }
        }

        return bakingService;
    }

}
