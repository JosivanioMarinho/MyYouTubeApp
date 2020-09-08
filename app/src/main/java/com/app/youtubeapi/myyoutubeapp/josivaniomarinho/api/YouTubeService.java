package com.app.youtubeapi.myyoutubeapp.josivaniomarinho.api;

import com.app.youtubeapi.myyoutubeapp.josivaniomarinho.model.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeService {

    /*

    https://www.googleapis.com/youtube/v3/
    search
    ?part=snippet
    &order=date
    &maxResults=20
    &key=AIzaSyAfOAY5h7zeOspDaibnV8TI_0jpCqoU9D4
    &channelId=UCcn0BFXHCtkqJl8CAPDj9og
    &q=texto digitado pelo usuario para pesquisa

   URL = https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&maxResults=20&key=AIzaSyAfOAY5h7zeOspDaibnV8TI_0jpCqoU9D4&channelId=UCcn0BFXHCtkqJl8CAPDj9og

    * */

    @GET("search")
    Call<Resultado> recuperarVideos(
            @Query("part") String part,
            @Query("order") String order,
            @Query("maxResults") String maxResults,
            @Query("key") String key,
            @Query("channelId") String channelId,
            @Query("q") String q
    );

}
