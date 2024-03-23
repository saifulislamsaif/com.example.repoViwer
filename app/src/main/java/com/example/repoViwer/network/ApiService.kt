package com.example.repoViwer.network


import com.example.repoViwer.apiResponseModel.RepoResponseModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/search/repositories?q=android")
    suspend fun getAllRepo(): RepoResponseModel
}