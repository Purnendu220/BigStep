package com.bigstep.restapi

import com.bigstep.model.ResponseModel
import com.bigstep.restapi.RetrofitClient.SEARCH_URL
import com.bigstep.restapi.RetrofitClient.TERM_MEDIA_TYPE
import com.bigstep.restapi.RetrofitClient.TERM_QUERY
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(SEARCH_URL)
    fun getMusicVideosAsync(@Query(TERM_QUERY) term:String, @Query(TERM_MEDIA_TYPE) mediaType:String) : Deferred<ResponseModel>
}