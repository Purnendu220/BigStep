package com.bigstep.ui.fragments.history

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bigstep.model.Album
import com.bigstep.repository.MusicVideoLocalRepository

class HistoryViewModel : ViewModel() {
    var liveDataAlbum: LiveData<List<Album>>? = null


    fun getAlbumList(context: Context) : LiveData<List<Album>>? {
        liveDataAlbum = MusicVideoLocalRepository.getAlbumList(context)
        return liveDataAlbum
    }
}