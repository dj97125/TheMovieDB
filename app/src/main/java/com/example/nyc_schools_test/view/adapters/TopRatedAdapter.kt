package com.example.nyc_schools_test.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nyc_schools_test.common.OnMovieClicked
import com.example.nyc_schools_test.common.URL_IMAGES
import com.example.nyc_schools_test.databinding.ItemLayoutBinding
import com.example.nyc_schools_test.domain.responses.TopRatedDomain
import com.squareup.picasso.Picasso

class TopRatedAdapter(
    private val onMovieClicked: OnMovieClicked,
    private val items: MutableList<TopRatedDomain> = mutableListOf()
) : RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder>() {

    class TopRatedViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateData(newItems: List<TopRatedDomain>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun erraseData() {
        items.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {
        return TopRatedViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        Picasso.get()
            .load(URL_IMAGES + items[position].posterPath)
            .into(holder.binding.moviePoster)

        holder.binding.movieView.setOnClickListener {
            onMovieClicked.topRatedClicked(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}