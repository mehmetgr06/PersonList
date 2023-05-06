package com.example.personlist.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personlist.data.source.FetchError

abstract class BaseViewModel : ViewModel() {

    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun handleDataError(fetchError: FetchError?) {
        fetchError?.let {
            _errorLiveData.value = it.errorDescription
        }
    }

}