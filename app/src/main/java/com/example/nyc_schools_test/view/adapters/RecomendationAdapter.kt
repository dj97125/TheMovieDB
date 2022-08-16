package com.example.nyc_schools_test.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nyc_schools_test.common.OnMovieClicked
import com.example.nyc_schools_test.common.URL_IMAGES
import com.example.nyc_schools_test.databinding.ItemLayoutBinding
import com.example.rappitest.model.remote.upcoming_response.RecomendationDomain
import com.squareup.picasso.Picasso

class RecomendationAdapter(
    private val onMovieClicked: OnMovieClicked,
    private val items: MutableList<RecomendationDomain> = mutableListOf()
) : RecyclerView.Adapter<RecomendationAdapter.RecomendationViewHolder>() {

    class RecomendationViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateData(newItems: List<RecomendationDomain>) {
        items.addAll(newItems.take(6))
        notifyDataSetChanged()
    }

    fun erraseData() {
        items.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomendationViewHolder {
        return RecomendationViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecomendationViewHolder, position: Int) {
        Picasso.get()
            .load(URL_IMAGES + items[position].posterPath)
            .into(holder.binding.moviePoster)



        holder.binding.movieView.setOnClickListener {
            onMovieClicked.recomendationClicked((items[position]))
        }
    }

    override fun getItemCount(): Int = items.size
}