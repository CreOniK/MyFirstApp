package com.example.bisirkinmyfirstapp.repository
import androidx.lifecycle.LiveData
import com.example.bisirkinmyfirstapp.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun increaseViews(id: Long)
    fun save(post: Post)
    fun removeById(id: Long)
}