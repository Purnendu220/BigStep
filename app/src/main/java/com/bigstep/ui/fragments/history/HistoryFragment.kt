package com.bigstep.ui.fragments.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigstep.R
import com.bigstep.adapters.VideoListAdapter
import com.bigstep.model.Album
import kotlinx.android.synthetic.main.no_data_layout.*
import kotlinx.android.synthetic.main.video_fragment.*

class HistoryFragment : Fragment() {
    private val TAG:String = "HistoryFragment";
    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var videoListAdapter: VideoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.history_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        initRecyclerView()
        initView();
        activity?.let { viewModel.getAlbumList(it) }!!.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onActivityCreated: ${it.size}")
            videoListAdapter.addAllItem(it)
            videoListAdapter.notifyDataSetChanged()
            handleNoDataView()

        })
    }
     private fun initView(){
         buttonRetry.visibility = View.GONE
         textMessage.text = getString(R.string.no_tracks_in_your_history)
     }
    private fun handleNoDataView(){

        if(videoListAdapter.itemCount==0){
            videoListRecyclerView.apply {
                visibility = View.GONE
            }
            noDataLayout.apply {
                visibility = View.VISIBLE


            }

        }else{
            videoListRecyclerView.apply {
                visibility = View.VISIBLE

            }
            noDataLayout.apply {
                visibility = View.GONE


            }
        }
    }


    private fun initRecyclerView(){
        videoListRecyclerView.apply {
            linearLayoutManager = LinearLayoutManager(activity)
            layoutManager = linearLayoutManager

            videoListAdapter = activity?.let { VideoListAdapter( mutableListOf<Album>()) }!!
            adapter = videoListAdapter



        }
    }

}