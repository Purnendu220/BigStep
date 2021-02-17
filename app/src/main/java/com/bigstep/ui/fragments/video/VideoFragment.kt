package com.bigstep.ui.fragments.video

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigstep.R
import com.bigstep.adapters.VideoListAdapter
import com.bigstep.model.Album
import kotlinx.android.synthetic.main.no_data_layout.*
import kotlinx.android.synthetic.main.video_fragment.*


class VideoFragment : Fragment(), View.OnClickListener {
     val TAG = "VideoFragment";

    companion object {
        fun newInstance() = VideoFragment()
    }

    private lateinit var viewModel: VideoViewModel
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var videoListAdapter: VideoListAdapter
    private lateinit var progressBar:ProgressBar;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.video_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VideoViewModel::class.java)
        initRecyclerView()
        initLoader()
        buttonRetry.setOnClickListener(this)
        viewModel.getMusicVideosLiveData()!!.observe(viewLifecycleOwner, Observer { videoData ->
            Log.d(TAG, "onActivityCreated: ${videoData.resultCount}")
            videoData.errorMessage.let {
                textMessage.text = it
            }
            videoListAdapter.addAllItem(videoData.results)
            videoListAdapter.notifyDataSetChanged()
            handleNoDataView()

        })
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
    private fun initLoader(){
        progressBar = ProgressBar(activity, null, android.R.attr.progressBarStyleLarge)
        val params = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        parent.addView(progressBar, params)
        viewModel.getIsLoading()!!.observe(viewLifecycleOwner, Observer {
            if (it!!) {
              showLoader()
            } else {
             hideLoader()
            }

        })
    }

    fun showLoader(){
        progressBar.visibility = View.VISIBLE;


    }

    fun hideLoader(){
        progressBar.visibility = View.GONE;

    }

    override fun onClick(v: View?) {
        when(v?.id){
        R.id.buttonRetry->{
            viewModel.getMusicVideosLiveData()
        }

        }
    }


}