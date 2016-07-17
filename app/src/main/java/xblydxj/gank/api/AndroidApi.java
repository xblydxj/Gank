package xblydxj.gank.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xblydxj.gank.bean.Android;

/**
 * Created by 46321 on 2016/7/17/017.
 */
public interface AndroidApi {
    @GET("data/Android/{count}/{page}")
    Call<Android> android(@Path("count") int count,
                          @Path("page") int page);
}
