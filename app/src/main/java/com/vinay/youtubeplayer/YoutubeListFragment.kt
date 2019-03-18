package com.vinay.youtubeplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_youtube_list.*
import kotlinx.android.synthetic.main.row_player_list.view.*

class YoutubeListFragment : Fragment() {

    var allPlayer = ArrayList<Player>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_youtube_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        how_to_play_list.adapter = BaseAdapter(
            layoutId = R.layout.row_player_list,
            list = allPlayer,
            viewHolder = { holder, item ->
                holder.itemView.apply {
                    holder.itemView.tv_player_title.text = item.title
                    Glide.with(context).load("https://img.youtube.com/vi/" + item.link + "/0.jpg")
                        .apply(RequestOptions().centerCrop()).into(holder.itemView.video_thumbnail_image)
                }
            },
            clickableView = R.id.play_btn,
            clickListener = { _, position ->
                (activity as MainActivity).playVideo(allPlayer[position].link)
            }
        )
        getList()
    }

    private fun getList() {
        allPlayer.clear()
        var index = 1
        allPlayer.add(Player("" + index++ + ". Android Q", "xCR-NzxP9Q0"))
        allPlayer.add(Player("" + index++ + ". Android P", "NNeJR14kgTY"))
        allPlayer.add(Player("" + index++ + ". Android Pie vs Oreo", "b0t5xDG0tKU"))
        how_to_play_list.adapter?.notifyDataSetChanged()
    }

    class Player(val title: String, val link: String)
}

