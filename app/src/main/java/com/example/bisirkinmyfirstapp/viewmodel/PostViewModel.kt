package com.example.bisirkinmyfirstapp.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bisirkinmyfirstapp.db.AppDb
import com.example.bisirkinmyfirstapp.dto.Post
import com.example.bisirkinmyfirstapp.repository.PostRepository
import com.example.bisirkinmyfirstapp.repository.PostRepositoryFileImpl
import com.example.bisirkinmyfirstapp.repository.PostRepositoryInMemoryImpl
import com.example.bisirkinmyfirstapp.repository.PostRepositorySQLiteImpl

class PostViewModel(application: Application) : AndroidViewModel(application) {

    // Используем файловую реализацию с передачей контекста приложения
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
    private val _draft = MutableLiveData("")
    val draft: LiveData<String> = _draft


    val data: LiveData<List<Post>> = repository.getAll()

    fun saveDraft(text: String) {
        _draft.value = text
    }

    fun clearDraft(text: String) {
        _draft.value = ""
    }

    private val empty = Post(
        id = 0,
        author = "",
        content = "",
        published = ""
    )

    private val _edited = MutableLiveData(empty)
    val edited: LiveData<Post> = _edited

    private val _editingMode = MutableLiveData(false)
    val editingMode: LiveData<Boolean> = _editingMode

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun increaseViews(id: Long) = repository.increaseViews(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun save() {
        _edited.value?.let { post ->
            if (post.content.isNotBlank()) {
                repository.save(post)
            }
        }
        _edited.value = empty
        _editingMode.value = false
    }

    fun edit(post: Post) {
        _edited.value = post
        _editingMode.value = true
    }

    fun changeContent(content: String) {
        val text = content.trim()
        _edited.value?.let { post ->
            if (post.content != text) {
                _edited.value = post.copy(content = text)
            }
        }
    }
    fun saveEditedPost(postId: Long, newContent: String) {
        val currentPosts = data.value ?: return
        val existingPost = currentPosts.find { it.id == postId } ?: return
        val updatedPost = existingPost.copy(content = newContent)
        repository.save(updatedPost)
        _edited.value = empty
        _editingMode.value = false
    }
    fun cancelEdit() {
        _edited.value = empty
        _editingMode.value = false
    }
}