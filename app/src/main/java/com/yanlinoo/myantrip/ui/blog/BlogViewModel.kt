package com.yanlinoo.myantrip.ui.blog

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanlinoo.myantrip.model.PostItem
import com.yanlinoo.myantrip.repository.BlogRepository
import kotlinx.coroutines.launch

class BlogViewModel : ViewModel() {

    lateinit var blogRepository: BlogRepository

    init {
        blogRepository = BlogRepository()
    }
    fun getAllPostsList(): LiveData<List<PostItem>>
    {
        return blogRepository.getAllPost()
    }

    fun getPostsFromAPIAndStore()
    {
        viewModelScope.launch{
            blogRepository.Blog_ApiCallAndPutInDB()
        }
    }
}