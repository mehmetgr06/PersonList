package com.example.personlist.util

import android.view.LayoutInflater
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

@MainThread
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T,
) = lazy {
    bindingInflater.invoke(layoutInflater)
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, observer: (T) -> Unit) {
    liveData?.observe(this, Observer(observer))
}