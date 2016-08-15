package xblydxj.gank.api;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;
import xblydxj.gank.bean.SearchResult;

/**
 * Created by xblydxj.
 * on 2016/8/13/013
 */
public interface SearchApi {
    @Headers("Cache-Control: public, max-age=3600")
    @GET("search/query/{search}/category/{type}/count/{count}/page/{page}")
    Observable<SearchResult> search(@Path("search")String search,
                                    @Path("type")String type,
                                    @Path("count")int count,
                                    @Path("page")int page);
}
