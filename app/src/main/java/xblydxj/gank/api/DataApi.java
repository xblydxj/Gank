package xblydxj.gank.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import xblydxj.gank.bean.Data;

/**
 * Created by xblydxj
 * on 2016/7/17/017.
 */
public interface DataApi {
    @GET("data/{type}/{count}/{page}")
    Observable<Data> getNormal(@Path("type") String type,
                               @Path("count") int count,
                               @Path("page") int page);

}
