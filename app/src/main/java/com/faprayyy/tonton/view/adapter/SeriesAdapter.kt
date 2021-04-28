package com.faprayyy.tonton.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faprayyy.tonton.R
import com.faprayyy.tonton.api.Config
import com.faprayyy.tonton.data.MovieModel
import com.faprayyy.tonton.data.SeriesModel
import com.faprayyy.tonton.databinding.ItemMovieBinding

class SeriesAdapter : RecyclerView.Adapter<SeriesAdapter.SeriesHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val mData = ArrayList<SeriesModel>()

    fun setData(items: ArrayList<SeriesModel>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class SeriesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)
        fun bind(serie: SeriesModel) {
            binding.title.text = serie.name
            binding.overview.text = serie.overview
            val posterImg = serie.posterPath?.let { Config.getPosterPath(it) }
            Glide.with(itemView.context)
                .load(posterImg)
                .apply(RequestOptions().override(1200,2800))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return SeriesHolder(view)
    }

    override fun onBindViewHolder(holder: SeriesHolder, position: Int) {
        holder.bind(mData[position])
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(mData[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: SeriesModel)
    }
}