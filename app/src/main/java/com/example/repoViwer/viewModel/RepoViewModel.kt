package com.example.repoViwer.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.repoViwer.apiResponseModel.RepoResponseModel
import com.example.repoViwer.network.ApiService
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepoViewModel @Inject constructor(
    private val apiService: ApiService
) : BaseViewModel() {

    val repoResponseLiveData = MutableLiveData<RepoResponseModel>()
    val errorMessageLiveData = MutableLiveData<String>()

    fun fetchRepositories() {
        viewModelScope.launch {
            try {
                val repoResponse = apiService.getAllRepo()
                repoResponseLiveData.value = repoResponse
            } catch (e: Exception) {
                errorMessageLiveData.value = "Failed to fetch repositories: ${e.message}"
            }
        }
    }
}