package com.ss.search.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import java.lang.reflect.Constructor

class ViewModelWithArgumentsFactory(private val args: Bundle) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor: Constructor<T> = modelClass.getDeclaredConstructor(Bundle::class.java)
            return constructor.newInstance(args)
        } catch (e: Exception) {

            throw e
        }
    }
}