package com.yanlinoo.myantrip.api

import com.yanlinoo.myantrip.model.PostItem
import com.yanlinoo.myantrip.model.WordItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming

interface Api {

    @GET("/words.php")
    fun getAllWords() : Call<List<WordItem>>

    @GET("/getAllPost.php")
    fun getAllPosts() : Call<List<PostItem>>

    @Streaming
    @GET("/videos.zip")
    fun downloadFileWithFixedUrl(): Call<ResponseBody>

}