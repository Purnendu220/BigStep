package com.bigstep.utils

import java.util.concurrent.TimeUnit

object Utils {
    fun displayTime(milliseconds: Long):String {
        val s = milliseconds % 60
        val m = milliseconds / 60 % 60
        val h = milliseconds / (60 * 60) % 24

        return "$h:$m:$s"
    }

}