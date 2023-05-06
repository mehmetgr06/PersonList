package com.example.personlist.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@MainThread
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T,
) = lazy {
    bindingInflater.invoke(layoutInflater)
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, observer: (T) -> Unit) {
    liveData?.observe(this, Observer(observer))
}

fun Context.showErrorDialog(errorMessage: String, refreshClickListener: () -> Unit) {
    val scope = CoroutineScope(Dispatchers.Main)
    AlertDialog.Builder(this).apply {
        setMessage(errorMessage)
        setCancelable(false)
        setPositiveButton("Refresh") { _, _ ->
            scope.launch {
                refreshClickListener.invoke()
                delay(3000)
            }
        }
        create().show()
    }
}