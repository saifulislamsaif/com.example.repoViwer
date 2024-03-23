package com.example.repoViwer.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repoViwer.network.NetworkError
import com.example.repoViwer.utills.logPrint
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<String>()
    lateinit var application: Application

    //Six Parameter
    fun <b1 : Any, b2 : Any, b3 : Any, b4 : Any, b5 : Any, res : Any> getResponse(
        requesterMethod: suspend (b1, b2, b3, b4, b5) -> res,
        body1: b1,
        body2: b2,
        body3: b3,
        body4: b4,
        body5: b5,
        onResponseMethod: (response: res) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                onResponseMethod(requesterMethod(body1, body2, body3, body4, body5))
            } catch (errorMsg: Throwable) {
                catchError(errorMsg)
            }
            isLoading.value = false
        }
    }

    //Five Parameter
    fun <b1 : Any, b2 : Any, b3 : Any, b4 : Any, res : Any> getResponse(
        requesterMethod: suspend (b1, b2, b3, b4) -> res,
        body1: b1,
        body2: b2,
        body3: b3,
        body4: b4,
        onResponseMethod: (response: res) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                onResponseMethod(requesterMethod(body1, body2, body3, body4))
            } catch (errorMsg: Throwable) {
                catchError(errorMsg)
            }
            isLoading.value = false
        }
    }

    //Four Parameter
    fun <b1 : Any, b2 : Any, b3 : Any, res : Any> getResponse(
        requesterMethod: suspend (b1, b2, b3) -> res,
        body1: b1,
        body2: b2,
        body3: b3,
        onResponseMethod: (response: res) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                onResponseMethod(requesterMethod(body1, body2, body3))
            } catch (errorMsg: Throwable) {
                catchError(errorMsg)
            }
            isLoading.value = false
        }
    }

    //Three Parameter
    fun <b1 : Any, b2 : Any, res : Any> getResponse(
        requesterMethod: suspend (b1, b2) -> res,
        body1: b1,
        body2: b2,
        onResponseMethod: (response: res) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                onResponseMethod(requesterMethod(body1, body2))
            } catch (errorMsg: Throwable) {
                catchError(errorMsg)
            }
            isLoading.value = false
        }
    }

    //Two Parameter
    fun <b1 : Any, res : Any> getResponse(
        requesterMethod: suspend (b1) -> res,
        body: b1,
        onResponseMethod: (response: res) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                onResponseMethod(requesterMethod(body))
            } catch (errorMsg: Throwable) {
                catchError(errorMsg)
            }
            isLoading.value = false
        }
    }

    //singleParameter
    fun <U : Any> getResponse(
        requesterMethod: suspend () -> U,
        onResponseMethod: (response: U) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                onResponseMethod(requesterMethod())
            } catch (errorMsg: Throwable) {
                catchError(errorMsg)
            }
            isLoading.value = false
        }
    }

    private fun catchError(errorMsg: Throwable) {
        val errorMessage = NetworkError.getServerResponseMessage(errorMsg, application)
        networkError.value = errorMessage
        printApiResponse(errorMsg.localizedMessage)
    }

    private fun printApiResponse(errorMsg: String?) {
//        if (BuildConfig.DEBUG)
            logPrint(errorMsg)
    }

}