package com.bigstep.adapters.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bigstep.R
import com.bigstep.model.Album
import com.bigstep.ui.activities.albumdetail.AlbumDetailActivity
import com.bigstep.utils.ImageUtils
import com.bigstep.utils.Utils
import kotlinx.android.synthetic.main.activity_album_detail.*
import kotlinx.android.synthetic.main.video_item_view.view.*

class VideoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bind(album:Album){
        itemView.apply {
            album.let { it ->
                it.artworkUrl100?.let { ImageUtils.setImage(this.context, it,imageViewArtWork) }
                trackTime.text = it.trackTimeMillis?.let { Utils.displayTime(milliseconds = it) }
                textViewAlbumName.text = it.collectionName?:this.context.getString(R.string.collection_not_available)
                textViewTrackName.text = it.trackName?.let { it }
                trackPrice.text = it.trackPrice?.let { "$it ${album.currency}" }
            }
            setOnClickListener {
                AlbumDetailActivity.open(this.context,album);
            }
        }
    }
}