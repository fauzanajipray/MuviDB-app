package com.faprayyy.tonton.view.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faprayyy.tonton.R
import com.faprayyy.tonton.data.local.entity.MovieEntity
import com.faprayyy.tonton.data.remote.Config
import com.faprayyy.tonton.databinding.ItemMovieBinding

class MovieAdapter(private val listener : OnItemClickCallback) : PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(
    DIFF_CALLBACK
){

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean = oldItem.id == newItem.id
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean  = oldItem == newItem
        }
    }


    inner class MovieViewHolder(val binding: ItemMovieBinding) :  RecyclerView.ViewHolder(binding.root){
        fun bind(movie : MovieEntity) {
            with(binding){
                title.text = movie.title
                overview.text = movie.overview
                val posterImg = movie.posterPath.let { Config.getPosterPath(it) }
                Glide.with(itemView.context)
                    .load(posterImg)
                    .apply(RequestOptions().override(1200,2800))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(this.imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position) as MovieEntity
        movie.apply {
            holder.bind(this)
            holder.binding.container.setOnClickListener {
                listener.onItemClicked(movie)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MovieEntity)
    }


}