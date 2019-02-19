package com.ss.search.viewmodel

import android.arch.lifecycle.ViewModel
import android.app.Application
import android.arch.lifecycle.ViewModelProvider


class ViewModelFactory(private val mApplication: Application, private val param : String) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass == SearchListViewModel::class.java) {
            SearchListViewModel(mApplication,param as String) as T
        }  else {
            super.create(modelClass)
        }
    }
}
