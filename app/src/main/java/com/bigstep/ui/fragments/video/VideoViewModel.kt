package com.bigstep.ui.fragments.video

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bigstep.model.ResponseModel
import com.bigstep.repository.MusicVideoRemoteRepository
import com.bigstep.restapi.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {
    var videosData=MutableLiveData<ResponseModel>()
    private val mIsLoading = MutableLiveData<Boolean>()


    fun getMusicVideosLiveData():LiveData<ResponseModel>?{
        mIsLoading.postValue(true);
        CoroutineScope(IO).launch {
         val result =    MusicVideoRemoteRepository.getMusicVideosFromApi(
             RetrofitClient.SEARCH_TERM,
             RetrofitClient.MEDIA_TYPE
         )
            try{
                videosData.postValue(result.await())
                result.invokeOnCompletion {
                    mIsLoading.postValue(false);

                }


        }catch (e: Exception){
                Log.d("VIEWMODEL", "getMusicVideosLiveData: ${e.message}")
                videosData.postValue(ResponseModel(0, mutableListOf(),e.message))
                mIsLoading.postValue(false);

            }
        }
      return videosData;
    }
    fun getIsLoading(): LiveData<Boolean?>? {
        return mIsLoading
    }

}