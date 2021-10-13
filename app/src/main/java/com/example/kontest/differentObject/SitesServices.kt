package com.example.kontest.differentObject

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://kontests.net"

interface SitesInterface{
    @GET("/api/v1/codeforces")
    fun getCf() : Call<List<ContestInfo>>

    @GET("/api/v1/codeforces_gym")
    fun getCfg() :Call<List<ContestInfo>>

    @GET("/api/v1/top_coder")
    fun getTc() : Call<List<ContestInfo>>

    @GET("/api/v1/at_coder")
    fun getAc() : Call<List<ContestInfo>>

    @GET("/api/v1/code_chef")
    fun getCc() : Call<List<ContestInfo>>

    @GET("/api/v1/hacker_rank")
    fun getHr() : Call<List<ContestInfo>>

    @GET("/api/v1/hacker_earth")
    fun getHe() : Call<List<ContestInfo>>

    @GET("/api/v1/kick_start")
    fun getGks() : Call<List<ContestInfo>>

    @GET("/api/v1/leet_code")
    fun getLe() : Call<List<ContestInfo>>
}

object SitesServices {
    val sitesInterface : SitesInterface

    init{
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        sitesInterface = retrofit.create(SitesInterface::class.java)
    }
}