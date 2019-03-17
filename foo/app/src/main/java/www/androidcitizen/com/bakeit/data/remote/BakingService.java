package www.androidcitizen.com.bakeit.data.remote;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import www.androidcitizen.com.bakeit.data.model.Recipe;

/**
 * Created by Mahi on 01/07/18.
 * www.androidcitizen.com
 */

public interface BakingService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

}
