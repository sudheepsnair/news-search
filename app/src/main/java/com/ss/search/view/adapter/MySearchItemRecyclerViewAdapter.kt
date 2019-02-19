package com.ss.search.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ss.search.R
import com.ss.search.model.Multimedia

import com.ss.search.view.ui.SearchItemListFragment.OnListFragmentInteractionListener
import com.ss.search.model.News
import com.ss.search.view.ui.glide.GlideApp
import kotlinx.android.synthetic.main.fragment_search_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [News] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MySearchItemRecyclerViewAdapter(
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MySearchItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var mValues: List<News>? = null

    init {

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as News
            mListener?.onListFragmentInteraction(item)
        }
    }

    fun setNews(news: List<News>) {
        mValues = news
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_search_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues?.get(position)
        var thumbnailUrl: String? = null
        var multimedium: List<Multimedia>? = item?.multimedia
        if (multimedium != null && multimedium.size > 0) {

            for (multiMediaItem in multimedium) {
                print(Multimedia)
                if (multiMediaItem.subtype.equals("thumbnail")) {
                    thumbnailUrl = multiMediaItem.url
                    if (thumbnailUrl != null) {
                        thumbnailUrl = "https://www.nytimes.com/" + thumbnailUrl
                    }
                    break;
                }
            }

        }

        GlideApp.with(holder.mThumbIcon.context)
            .load(thumbnailUrl?.let { thumbnailUrl } ?: R.drawable.ic_search_24pixel)
            .placeholder(R.drawable.ic_search_24pixel)
            .error(R.drawable.ic_search_24pixel)
            .into(holder.mThumbIcon);

        holder.mContentView.text = item?.headline?.main ?: "Head Line"

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues?.size ?: 0

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mThumbIcon: ImageView = mView.thump_icon
        val mContentView: TextView = mView.headline

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
