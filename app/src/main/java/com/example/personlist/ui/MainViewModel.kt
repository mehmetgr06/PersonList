package com.example.personlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.personlist.common.BaseViewModel
import com.example.personlist.data.source.Person
import com.example.personlist.domain.GetPersonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getPersonsUseCase: GetPersonsUseCase) :
    BaseViewModel() {

    private val _personsLiveData: MutableLiveData<PagingData<Person>> = MutableLiveData()
    val personsLiveData: LiveData<PagingData<Person>> = _personsLiveData

    fun getPersons(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            getPersonsUseCase(isRefreshing, handleError = {
                handleDataError(it)
            }).cachedIn(this).collectLatest { personsData ->
                _personsLiveData.value = personsData
            }
        }
    }
}