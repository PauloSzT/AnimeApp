package com.example.anime

import android.app.Application
import com.example.anime.ui.fetcher.FetcherListener
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){

    var fetcherListener: FetcherListener? = null

    @JvmName("animeFetcherListener")
    fun setFetcherListener(value: FetcherListener){
        fetcherListener = value
    }

    fun startFetcher(){
        fetcherListener?.let {fetcher ->
            if(fetcher.isIdle()){
                fetcher.startFetching()
            }
        }
    }

    fun stopFetcher(){
        fetcherListener?.let {fetcher ->
            if(!fetcher.isIdle()){
                fetcher.stopFetching()
            }
        }
    }
}
