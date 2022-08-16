package com.example.nyc_schools_test.domain

import com.example.nyc_schools_test.model.remote.Repository
import javax.inject.Inject

class RecomendationUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(response: String) = repository.RecomendationCatched(response)
}