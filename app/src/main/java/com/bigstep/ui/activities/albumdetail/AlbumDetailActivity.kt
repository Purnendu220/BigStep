package com.bigstep.ui.activities.albumdetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bigstep.R
import com.bigstep.model.Album
import com.bigstep.utils.ImageUtils
import com.bigstep.utils.Utils
import kotlinx.android.synthetic.main.activity_album_detail.*

class AlbumDetailActivity : AppCompatActivity() {
    private val TAG:String = "AlbumDetailActivity";

    companion object{
        const val EXTRA_DATA = "album_data"

        fun open(context:Context,album:Album){
            val intent = Intent(context,AlbumDetailActivity::class.java)
            intent.putExtra(EXTRA_DATA,album)
            context.startActivity(intent)

        }

    }

    lateinit var mainActivityViewModel: AlbumDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)
        mainActivityViewModel = ViewModelProvider(this).get(AlbumDetailViewModel::class.java)
        val albumData: Album? = intent.getSerializableExtra(EXTRA_DATA) as Album?
        Log.d(TAG, "onActivityCreated: ${albumData?.artistName}")

        albumData?.let { it ->
            mainActivityViewModel.insertAlbumData(this, it).observe(this, Observer {
                Log.d(TAG, "onActivityCreated: ${it.artistName}")
                setupUi(it)
            })
        }


    }

    private fun setupUi(album: Album)
    {
        album.let { it ->

            trackTime.text = it.trackTimeMillis?.let { Utils.displayTime(milliseconds = it) }
            textViewAlbumName.text = it.collectionName?:getString(R.string.collection_not_available)
            textViewTrackName.text = it.trackName?.let { it }
            textViewArtistName.text = it.artistName?.let{ it }
            it.trackViewCount.let { if(it==null){trackViewCount.text ="1 ${getString(R.string.view_by_you)}"}else{
                trackViewCount.text = "$it ${getString(R.string.view_by_you)}"
            } }
            trackPrice.text = it.trackPrice?.let { "$it ${album.currency}" }
            collectionTrackCount.text = it.primaryGenreName?.let { it }
            it.artworkUrl100.let {
                if (it != null) {
                    ImageUtils.setImage(this,it,imageViewArtWork)
                }
            }

        }




    }


}