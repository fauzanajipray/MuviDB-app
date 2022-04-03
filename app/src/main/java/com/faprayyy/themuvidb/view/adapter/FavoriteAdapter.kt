package com.faprayyy.themuvidb.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faprayyy.themuvidb.R
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.data.remote.Config
import com.faprayyy.themuvidb.databinding.ItemFavoriteBinding
import com.faprayyy.themuvidb.view.ui.detail.detailmovie.DetailMovieActivity
import com.faprayyy.themuvidb.view.ui.detail.detailmovie.DetailMovieActivity.Companion.MOVIE_TYPE
import com.faprayyy.themuvidb.view.ui.detail.detailseries.DetailSeriesActivity

class FavoriteAdapter(private val activity: Activity) : PagedListAdapter<FavoriteEntity, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :  RecyclerView.ViewHolder(binding.root){
        fun bind(fav : FavoriteEntity) {
            with(binding){
                tvName.text = fav.name
                tvType.text = activity.getString(R.string.type_x, fav.type)

                val posterImg = fav.posterPath?.let { Config.getPosterPath(it) }
                Glide.with(itemView.context)
                    .load(posterImg)
                    .apply(RequestOptions().override(120,280))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.imgPoster)

                layoutConstraint.setOnClickListener {
                    if (fav.type == MOVIE_TYPE){
                        val intent = Intent(activity, DetailMovieActivity::class.java)
                        intent.putExtra(DetailMovieActivity.EXTRA_FAVORITE, fav)
                        activity.startActivity(intent)
                    } else {
                        val intent = Intent(activity, DetailSeriesActivity::class.java)
                        intent.putExtra(DetailSeriesActivity.EXTRA_FAVORITE, fav)
                        activity.startActivity(intent)
                    }
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteEntity> = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean {
                return oldItem.name == newItem.name && oldItem.type == newItem.type
            }

            override fun areContentsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position) as FavoriteEntity)
    }

}
