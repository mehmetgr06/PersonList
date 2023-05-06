package com.example.personlist.ui

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.personlist.common.BaseActivity
import com.example.personlist.databinding.ActivityMainBinding
import com.example.personlist.ui.adapter.PersonsAdapter
import com.example.personlist.util.observe
import com.example.personlist.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val mainViewModel: MainViewModel by viewModels()
    private val personsAdapter = PersonsAdapter()

    override fun getLayout(): ViewBinding = binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.getPersons()
        binding.recyclerPerson.adapter = personsAdapter
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        binding.apply {
            with(mainViewModel) {
                observe(personsLiveData) { personData ->
                    lifecycleScope.launch {
                        personsAdapter.submitData(personData)
                    }
                }
                personsAdapter.addLoadStateListener { loadState ->
                    if (loadState.refresh is LoadState.Loading ||
                        loadState.append is LoadState.Loading
                    ) {
                        progressPaging.visibility = View.VISIBLE
                    } else {
                        progressPaging.visibility = View.GONE
                    }
                    if (loadState.append.endOfPaginationReached && personsAdapter.itemCount < 1) {
                        textEmptyView.visibility = View.VISIBLE
                    }
                }

            }
        }
    }

    private fun initListeners() {
        binding.swipeRefresh.apply {
            setOnRefreshListener {
                mainViewModel.getPersons(isRefreshing = true)
                isRefreshing = false
            }
        }
    }
}