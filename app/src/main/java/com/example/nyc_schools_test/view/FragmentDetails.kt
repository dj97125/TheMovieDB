package com.example.nyc_schools_test.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nyc_schools_test.common.BaseFragment
import com.example.nyc_schools_test.common.URL_IMAGES
import com.example.nyc_schools_test.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso


class FragmentDetails : BaseFragment() {


    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val upcomingInfo = viewModel.upcoming
        val topRatedInfo = viewModel.topRated
        val recomendationInfo = viewModel.recomendation

        binding.apply {
            if (recomendationInfo != null) {
                val rate = (recomendationInfo?.voteAverage / 2)
                Picasso.get()
                    .load(URL_IMAGES + recomendationInfo?.posterPath)
                    .into(backgroundDetails)
                detailsOriginalTitle.text = recomendationInfo?.originalTitle
                detailsTitle.text = recomendationInfo?.title
                relaseDateTXT.text = recomendationInfo?.releaseDate.take(4)
                languajeTXT.text = recomendationInfo?.originalLanguage
                rateTXT.text = rate.toString()
                detailsOverView.text = recomendationInfo?.overview
            }
        }


        binding.apply {
            if (upcomingInfo != null) {
                val rate = (upcomingInfo?.voteAverage / 2)
                Picasso.get()
                    .load(URL_IMAGES + upcomingInfo?.posterPath)
                    .into(backgroundDetails)
                detailsOriginalTitle.text = upcomingInfo?.originalTitle
                detailsTitle.text = upcomingInfo?.title
                relaseDateTXT.text = upcomingInfo?.releaseDate.take(4)
                languajeTXT.text = upcomingInfo?.originalLanguage
                rateTXT.text = rate.toString()
                detailsOverView.text = upcomingInfo?.overview


            }
        }


        binding.apply {
            if (topRatedInfo != null) {
                val rate = (topRatedInfo?.voteAverage / 2)
                Picasso.get()
                    .load(URL_IMAGES + topRatedInfo?.posterPath)
                    .into(backgroundDetails)
                detailsOriginalTitle.text = topRatedInfo?.originalTitle
                detailsTitle.text = topRatedInfo?.title
                relaseDateTXT.text = topRatedInfo?.releaseDate.take(4)
                languajeTXT.text = topRatedInfo?.originalLanguage
                rateTXT.text = rate.toString()
                detailsOverView.text = topRatedInfo?.overview
            }
        }


        return binding.root
    }

}