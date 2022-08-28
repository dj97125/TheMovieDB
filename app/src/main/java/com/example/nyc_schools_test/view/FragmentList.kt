package com.example.nyc_schools_test.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nyc_schools_test.R
import com.example.nyc_schools_test.common.BaseFragment
import com.example.nyc_schools_test.common.OnMovieClicked
import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.databinding.FragmentListBinding
import com.example.nyc_schools_test.domain.responses.TopRatedDomain
import com.example.nyc_schools_test.domain.responses.UpcomingDomain
import com.example.nyc_schools_test.view.adapters.TopRatedAdapter
import com.example.nyc_schools_test.view.adapters.UpcomingAdapter
import com.example.rappitest.model.remote.upcoming_response.RecomendationDomain


class FragmentList : BaseFragment(), OnMovieClicked {


    private val binding by lazy {
        FragmentListBinding.inflate(layoutInflater)
    }
    private val upcomingAdapter by lazy {
        UpcomingAdapter(this)
    }
    private val recomendationAdapter by lazy {
        RecomendationAdapter(this)
    }
    private val topRatedAdapter by lazy {
        TopRatedAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.upcomingRecycler.apply {
            adapter = upcomingAdapter
        }
        binding.topRatedRecycler.apply {
            adapter = topRatedAdapter
        }
        binding.recomendedRecycler.apply {
            adapter = recomendationAdapter
        }
        binding.languajeButton.setOnClickListener {
            viewModel.getRecomendationList("ja")

        }
        binding.releasedButton.setOnClickListener {
            viewModel.getRecomendationList("2022")
        }

        viewModel.recomendationResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is StateAction.SUCCESS<*> -> {
                    val retrievedRecomendation = state.response as List<RecomendationDomain>
                    binding.apply {
                        swipeRefresh.visibility = View.VISIBLE
                        recomendedRecycler.visibility = View.VISIBLE
                        recomendationAdapter.erraseData()
                        recomendationAdapter.updateData(retrievedRecomendation)
                    }

                }
                is StateAction.ERROR -> {
                    binding.apply {
                        recomendedRecycler.visibility = View.GONE
                        swipeRefresh.visibility = View.GONE
                    }
                    displayErrors(state.error.localizedMessage) {
                        viewModel.getUpcomingList()
                    }
                }

            }
        }
        viewModel.upcomingResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is StateAction.SUCCESS<*> -> {
                    val retrievedMessage = state.message
                    showToastMessage(retrievedMessage)
                    val retrievedUpcomings = state.response as List<UpcomingDomain>
                    binding.apply {
                        swipeRefresh.visibility = View.VISIBLE
                        upcomingRecycler.visibility = View.VISIBLE
                        upcomingAdapter.erraseData()
                        upcomingAdapter.updateData(retrievedUpcomings)
                    }
                }
                is StateAction.ERROR -> {
                    binding.apply {
                        upcomingRecycler.visibility = View.GONE
                        swipeRefresh.visibility = View.GONE
                    }

                    displayErrors(state.error.localizedMessage) {
                        viewModel.getUpcomingList()
                    }
                }
            }
        }
        viewModel.topRatedResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is StateAction.SUCCESS<*> -> {
                    val retrievedMessage = state.message
                    showToastMessage(retrievedMessage)
                    val retrievedTopRated = state.response as List<TopRatedDomain>
                    binding.apply {
                        swipeRefresh.visibility = View.VISIBLE
                        topRatedRecycler.visibility = View.VISIBLE
                        topRatedAdapter.erraseData()
                        topRatedAdapter.updateData(retrievedTopRated)
                    }
                }
                is StateAction.ERROR -> {
                    binding.apply {
                        topRatedRecycler.visibility = View.GONE
                        swipeRefresh.visibility = View.GONE
                    }

                    displayErrors(state.error.localizedMessage) {
                        viewModel.getTopRatedList()
                    }
                }
            }
        }

        return binding.root
    }

    override fun upcomingClicked(upcoming: UpcomingDomain) {
        viewModel.upcoming = upcoming
        viewModel.topRated = null
        viewModel.recomendation = null
        findNavController().navigate(R.id.action_fragmentList_to_fragmentDetails)
    }

    override fun topRatedClicked(topRated: TopRatedDomain) {
        viewModel.topRated = topRated
        viewModel.upcoming = null
        viewModel.recomendation = null
        findNavController().navigate(R.id.action_fragmentList_to_fragmentDetails)
    }

    override fun recomendationClicked(recomendated: RecomendationDomain) {
        viewModel.recomendation = recomendated
        viewModel.upcoming = null
        viewModel.topRated = null
        findNavController().navigate(R.id.action_fragmentList_to_fragmentDetails)
    }

    override fun onResume() {
        super.onResume()
        binding.swipeRefresh.apply {
            setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_purple,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_bright,
                android.R.color.holo_red_light,
                android.R.color.holo_green_dark,
            )
            binding.swipeRefresh.setOnRefreshListener {
                viewModel.getTopRatedList()
                viewModel.getUpcomingList()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

}