package com.consumer.content.core.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    protected val viewModelScope: CoroutineScope by lazy {
        val errorHandler = CoroutineExceptionHandler(::handler)
        CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)
    }

    protected open fun handler(context: CoroutineContext, exception: Throwable) {}
}