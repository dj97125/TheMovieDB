package com.example.nyc_schools_test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.domain.RecomendationUseCase
import com.example.nyc_schools_test.domain.TopRatedUseCase
import com.example.nyc_schools_test.domain.UpcomingUseCase
import com.example.nyc_schools_test.domain.responses.TopRatedDomain
import com.example.nyc_schools_test.domain.responses.UpcomingDomain
import com.example.rappitest.model.remote.upcoming_response.RecomendationDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val handler: CoroutineExceptionHandler,
    private val upcomingUseCase: UpcomingUseCase,
    private val topRatedUseCase: TopRatedUseCase,
    private val recomendationUseCase: RecomendationUseCase,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _upcomingResponse = MutableLiveData<StateAction>()
    val upcomingResponse: MutableLiveData<StateAction>
        get() = _upcomingResponse

    private val _topRatedResponse = MutableLiveData<StateAction>()
    val topRatedResponse: MutableLiveData<StateAction>
        get() = _topRatedResponse

    private val _recomendationResponse = MutableLiveData<StateAction>()
    val recomendationResponse: MutableLiveData<StateAction>
        get() = _recomendationResponse

    var upcoming: UpcomingDomain? = null
    var topRated: TopRatedDomain? = null
    var recomendation: RecomendationDomain? = null

    init {
        getUpcomingList()
        getTopRatedList()
    }



    fun getRecomendationList(response: String) {
        coroutineScope.launch(handler) {
            supervisorScope {
                launch {
                    recomendationUseCase(response).collect {
                        _recomendationResponse.postValue(it)
                    }
                }
            }
        }
    }

    fun getTopRatedList() {
        coroutineScope.launch(handler) {
            supervisorScope {
                launch {
                    topRatedUseCase().collect {
                        _topRatedResponse.postValue(it)
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun getUpcomingList() {
        coroutineScope.launch(handler) {
            supervisorScope {
                launch {
                    upcomingUseCase().collect {
                        _upcomingResponse.postValue(it)
                    }
                }
            }
        }
    }


}




