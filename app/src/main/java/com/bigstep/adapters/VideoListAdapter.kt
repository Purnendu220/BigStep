package com.bigstep.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bigstep.R
import com.bigstep.adapters.viewholder.VideoItemViewHolder
import com.bigstep.model.Album

class VideoListAdapter( private var mList: MutableList<Album>) : RecyclerView.Adapter<VideoItemViewHolder>()  {


    fun addItem(item:Album){
        mList.add(item)
        notifyDataSetChanged()
    }
    fun addAllItem(items:List<Album>){
        mList.clear()
        mList.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): VideoItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.video_item_view, viewGroup, false)

        return VideoItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: VideoItemViewHolder, p1: Int) {
        viewHolder.bind(mList[p1])
    }

    override fun getItemCount(): Int {
       return mList.size
    }


}
