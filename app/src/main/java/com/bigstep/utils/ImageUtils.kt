package com.bigstep.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageUtils {

    fun setImage(context:Context,url:String,imageView:ImageView){
        Glide.with(context).load(url).into(imageView);
    }

}