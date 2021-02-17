package com.bigstep.ui.activities.albumdetail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bigstep.model.Album
import com.bigstep.repository.MusicVideoLocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AlbumDetailViewModel :ViewModel() {
    private var liveDataAlbum= MutableLiveData<Album>()

    fun insertAlbumData(context: Context, album: Album):LiveData<Album> {
        MusicVideoLocalRepository.insertData(context, album)
        CoroutineScope(IO).launch {
           val track = MusicVideoLocalRepository.getTrack(context,album)
            liveDataAlbum.postValue(track?:album)

        }
        return liveDataAlbum;
    }

}