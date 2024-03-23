package com.example.repoViwer.network


import android.app.Application
import android.content.Context
import com.example.repoViwer.R
import com.google.gson.JsonIOException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

object NetworkError {
    fun getServerResponseMessage(throwable: Throwable, application: Application): String {
        return getServerResponseMessage(throwable, application.baseContext)
    }

    private fun getServerResponseMessage(throwable: Throwable, context: Context): String {
        when {
            throwable is SocketTimeoutException -> {
                return context.resources.getString(R.string.time_out_error)
            }

            throwable is IOException -> {
                return context.resources.getString(R.string.turn_on_internet)
            }

            throwable is JsonIOException -> {
                return context.resources.getString(R.string.json_convert_error)
            }

            throwable is IllegalArgumentException -> {
                return throwable.message.toString()
            }

            throwable is CancellationException -> {
                return context.resources.getString(R.string.request_rejected)
            }

            throwable !is HttpException -> {
                throw throwable
            }

            getErrorCode(throwable) == 401 || getErrorCode(throwable) == 403 -> {
                return context.resources.getString(R.string.unauthorized_error)
            }

            getErrorCode(throwable) == 404 -> {
                return context.resources.getString(R.string.no_response_host)
            }

            throwable.message?.contains("422") == true -> {
                return context.resources.getString(R.string.required_data_missing_or_does_not_matched)
            }

            throwable.message?.contains("409") == true -> {
                return context.resources.getString(R.string.user_exist)
            }

            throwable.message?.contains("404") == true -> {
                return context.resources.getString(R.string.no_response_host)
            }

            throwable.message?.contains("500") == true -> {
                return context.resources.getString(R.string.required_data_missing_no_response_host)
            }

            else -> return context.resources.getString(R.string.unknown_error)
        }
    }

    private fun getErrorCode(throwable: Throwable): Int {
        return (throwable as HttpException).code()
    }
}