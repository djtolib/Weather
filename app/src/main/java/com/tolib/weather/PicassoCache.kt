package com.tolib.weather
import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class PicassoCache(context: Context) {
    private val picasso: Picasso by lazy {
        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build()
    }

    fun get(): Picasso {
        return picasso
    }
}
