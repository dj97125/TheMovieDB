package com.example.nyc_schools_test.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nyc_schools_test.common.OnMovieClicked
import com.example.nyc_schools_test.common.URL_IMAGES
import com.example.nyc_schools_test.databinding.ItemLayoutBinding
import com.example.nyc_schools_test.domain.responses.UpcomingDomain
import com.squareup.picasso.Picasso

class UpcomingAdapter(
    private val onMovieClicked: OnMovieClicked,
    private val items: MutableList<UpcomingDomain> = mutableListOf()
) : RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder>() {

    class UpcomingViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateData(newItems: List<UpcomingDomain>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun erraseData() {
        items.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        return UpcomingViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        Picasso.get()
            .load(URL_IMAGES + items[position].posterPath)
            .into(holder.binding.moviePoster)


        holder.binding.movieView.setOnClickListener {
            onMovieClicked.upcomingClicked(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}