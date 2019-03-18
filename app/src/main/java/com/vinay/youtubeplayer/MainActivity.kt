package com.vinay.youtubeplayer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MotionEvent
import com.vinay.youtubeplayer.youtubevideoplayer.util.YoutubePlayerHelper

class MainActivity : AppCompatActivity() {

    var helper: YoutubePlayerHelper? = null

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        helper?.onTouch(ev)
        return super.dispatchTouchEvent(ev)
    }

    fun playVideo(id: String) {
        helper?.playVideo(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(YoutubeListFragment(), "YoutubeListFragment")
        helper = YoutubePlayerHelper(
            resources,
            supportFragmentManager,
            findViewById(R.id.scroll_root),
            findViewById(R.id.container_root),
            findViewById(R.id.container)
        )
    }

    private fun loadFragment(fragment: Fragment, mTag: String) {
        val backStateName = fragment.javaClass.name
        val fragmentPopped = supportFragmentManager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped) {
            val transaction = supportFragmentManager.beginTransaction()
            try {
                transaction.replace(R.id.frame_layout, fragment, mTag)
                transaction.addToBackStack(backStateName)
                transaction.commit()
            } catch (e: Exception) {
                transaction.commitAllowingStateLoss()
            }
        }
    }
}
