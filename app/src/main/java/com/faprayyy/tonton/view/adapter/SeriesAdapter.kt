package com.faprayyy.tonton.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faprayyy.tonton.R
import com.faprayyy.tonton.data.local.entity.SeriesEntity
import com.faprayyy.tonton.data.remote.Config
import com.faprayyy.tonton.databinding.ItemMovieBinding

class SeriesAdapter(private val listener : OnItemClickCallback) : PagedListAdapter<SeriesEntity, SeriesAdapter.SeriesViewHolder>(
    DIFF_CALLBACK
) {

    interface OnItemClickCallback {
        fun onItemClicked(data: SeriesEntity)
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SeriesEntity>(){
            override fun areItemsTheSame(oldItem: SeriesEntity, newItem: SeriesEntity): Boolean = oldItem.id == newItem.id
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: SeriesEntity, newItem: SeriesEntity): Boolean  = oldItem == newItem
        }
    }

    inner class SeriesViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(series : SeriesEntity) {
            with(binding){
                title.text = series.title
                overview.text = series.overview
                val posterImg = series.posterPath.let { Config.getPosterPath(it) }
                Glide.with(itemView.context)
                    .load(posterImg)
                    .apply(RequestOptions().override(1200,2800))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(this.imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val series = getItem(position) as SeriesEntity
        series.apply {
            holder.bind(this)
            holder.binding.container.setOnClickListener {
                listener.onItemClicked(this)
            }
        }
    }

}