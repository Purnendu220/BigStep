package com.bigstep.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.bigstep.model.Album
import com.bigstep.room.AlbumDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MusicVideoLocalRepository {

    companion object {

        var albumDatabase: AlbumDatabase? = null

        var albumTableModel: LiveData<List<Album>>? = null

        fun initializeDB(context: Context) : AlbumDatabase {
            return AlbumDatabase.getDatabaseClient(context)
        }

        fun insertData(context: Context, album:Album) {
            albumDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                val isExist = album.trackId?.let { albumDatabase!!.albumDao().isTrackExist(it) }
                if(!isExist!!){
                    album.trackViewCount = 1
                    albumDatabase!!.albumDao().insertTrack(album)

                }else{
                    albumDatabase!!.albumDao().updateTrackView(album.trackId)
                }
            }

        }

        suspend fun getTrack(context: Context, album:Album): Album? {
            albumDatabase = initializeDB(context)
          val job =  CoroutineScope(IO).async {
                album.trackId?.let { albumDatabase!!.albumDao().getTrackById(it) }
            }
         return job.await()
        }

        fun getAlbumList(context: Context) : LiveData<List<Album>>? {

            albumDatabase = initializeDB(context)

            albumTableModel = albumDatabase!!.albumDao().getSavedTracks()

            return albumTableModel
        }

    }
}