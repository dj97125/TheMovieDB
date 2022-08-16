package com.example.nyc_schools_test.common

import com.example.nyc_schools_test.domain.responses.TopRatedDomain
import com.example.nyc_schools_test.domain.responses.UpcomingDomain
import com.example.rappitest.model.remote.upcoming_response.RecomendationDomain

interface OnMovieClicked {
    fun upcomingClicked(upcoming: UpcomingDomain)
    fun topRatedClicked(topRated: TopRatedDomain)
    fun recomendationClicked(recomendated: RecomendationDomain)
}