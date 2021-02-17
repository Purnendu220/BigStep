package com.bigstep.model

import java.io.Serializable

data class ResponseModel(
    val resultCount: Int,
    val results: List<Album>,
    var errorMessage:String?
):Serializable