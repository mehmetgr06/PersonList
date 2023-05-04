package com.example.personlist.ui

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.personlist.common.BaseActivity
import com.example.personlist.databinding.ActivityMainBinding
import com.example.personlist.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun getLayout(): ViewBinding = binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}