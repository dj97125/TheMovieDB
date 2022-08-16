package com.example.nyc_schools_test.model.remote

import com.example.nyc_schools_test.common.END_POINT
import com.example.rappitest.model.remote.upcoming_response.RecomendationResponse
import com.example.rappitest.model.remote.upcoming_response.TopRatedResponse
import com.example.rappitest.model.remote.upcoming_response.UpcomingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApi {

    @GET(END_POINT)
    suspend fun getUpcomingList(
        @Path("section") section: String = "upcoming",
        @Query("api_key") api_key: String = "01eb84b570be9a8e755c5ed0647e40ab",
    ): Response<UpcomingResponse>

    @GET(END_POINT)
    suspend fun getTopRatedList(
        @Path("section") section: String = "top_rated",
        @Query("api_key") api_key: String = "01eb84b570be9a8e755c5ed0647e40ab",
    ): Response<TopRatedResponse>

    @GET(END_POINT)
    suspend fun getRecomendationList(
        @Path("section") section: String = "popular",
        @Query("api_key") api_key: String = "01eb84b570be9a8e755c5ed0647e40ab",
    ): Response<RecomendationResponse>


}