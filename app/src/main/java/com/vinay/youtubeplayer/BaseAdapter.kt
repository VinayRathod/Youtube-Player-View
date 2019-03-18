package com.vinay.youtubeplayer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


open class BaseAdapter<T>() : RecyclerView.Adapter<BaseAdapter<T>.ViewHolder>() {
    private var layoutId: Int = 0
    private var list: List<T>? = null
    private var viewHolder: ((RecyclerView.ViewHolder, T) -> Unit)? = null
    private val clickableViews = ArrayList<Int>()
    private val longClickableView = ArrayList<Int>()
    private var clickListener: ((View, Int) -> Unit)? = null
    private var longClickListener: ((View, Int) -> Boolean)? = null

    constructor(layoutId: Int, list: List<T>?, viewHolder: ((RecyclerView.ViewHolder, T) -> Unit)) :
            this(layoutId, list, viewHolder, listOf(), null)

    constructor(layoutId: Int, list: List<T>?, viewHolder: ((RecyclerView.ViewHolder, T) -> Unit), clickableViews: List<Int>, clickListener: ((View, Int) -> Unit)?) :
            this() {
        this.layoutId = layoutId
        this.list = list
        this.viewHolder = viewHolder
        this.clickableViews.addAll(clickableViews)
        this.clickListener = clickListener
    }

    constructor(layoutId: Int, list: List<T>?, viewHolder: ((RecyclerView.ViewHolder, T) -> Unit), clickableView: Int, clickListener: ((View, Int) -> Unit)? = null) :
            this(layoutId, list, viewHolder, listOf(clickableView), clickListener)

    constructor(layoutId: Int, list: List<T>?, viewHolder: ((RecyclerView.ViewHolder, T) -> Unit), clickableView: Int, clickListener: ((View, Int) -> Unit)? = null,
                longClickableView: Int, longClickListener: ((View, Int) -> Boolean)? = null) :
            this() {
        this.layoutId = layoutId
        this.list = list
        this.viewHolder = viewHolder
        this.clickableViews.add(clickableView)
        this.clickListener = clickListener
        this.longClickableView.add(longClickableView)
        this.longClickListener = longClickListener
    }

    override fun getItemCount() = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewHolder?.apply { this(holder, list!![position]) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            for (clickableView in clickableViews)
                view.findViewById<View>(clickableView).setOnClickListener { v -> clickListener?.invoke(v, adapterPosition) }
            for (clickableView in longClickableView)
                view.findViewById<View>(clickableView).setOnLongClickListener { v -> longClickListener?.invoke(v, adapterPosition)!! }
        }
    }
}