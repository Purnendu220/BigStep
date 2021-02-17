package com.bigstep.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bigstep.model.Album

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(album: Album)

    @Query("SELECT * FROM ALBUM")
    fun getSavedTracks() : LiveData<List<Album>>

    @Query("SELECT * FROM ALBUM WHERE trackId == :id")
    fun isTrackExist(id: Int): Boolean

    @Query("SELECT * FROM ALBUM WHERE trackId == :id")
    fun getTrackById(id: Int): Album

    @Query("UPDATE ALBUM SET trackViewCount = trackViewCount + 1 WHERE trackId = :id")
    fun updateTrackView(id:Int)

}