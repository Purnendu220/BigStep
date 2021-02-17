package com.bigstep.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bigstep.model.ResponseModel
import com.bigstep.restapi.RetrofitClient
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MusicVideoRemoteRepository {

    fun getMusicVideosFromApi(term:String,mediaType:String): Deferred<ResponseModel> {
        val call = RetrofitClient.apiInterface.getMusicVideosAsync(term,mediaType);
        return call;
    }


}