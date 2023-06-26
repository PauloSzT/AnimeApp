package com.example.anime.ui.fetcher

import androidx.test.espresso.IdlingResource

class FetchingIdlingResource : IdlingResource, FetcherListener {

    private var idle = true
    private var resourceCallBack: IdlingResource.ResourceCallback? = null
    override fun getName(): String {
        return FetchingIdlingResource::class.java.simpleName
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallBack = callback
    }

    override fun isIdleNow(): Boolean = idle

    override fun startFetching() {
        idle = false
    }

    override fun stopFetching() {
        idle = true
        resourceCallBack?.onTransitionToIdle()
    }

    override fun isIdle(): Boolean = idle
}

interface FetcherListener {
    fun startFetching()
    fun stopFetching()

    fun isIdle(): Boolean
}
