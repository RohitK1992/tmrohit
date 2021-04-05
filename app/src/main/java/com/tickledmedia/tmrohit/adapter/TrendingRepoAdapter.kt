package com.tickledmedia.tmrohit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tickledmedia.tmrohit.R
import com.tickledmedia.tmrohit.models.ResponseItem
import com.tickledmedia.tmrohit.viewholder.TrendingRepoViewHolder

class TrendingRepoAdapter (private var trendingRepoList : List<ResponseItem>) : RecyclerView.Adapter<TrendingRepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingRepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trending_repository, parent, false)
        return TrendingRepoViewHolder(view)
    }


    override fun onBindViewHolder(holder: TrendingRepoViewHolder, position: Int) {
        holder.bindDetailsToView(trendingRepoList[position])
    }

    override fun getItemCount(): Int {
        return trendingRepoList.size
    }
}