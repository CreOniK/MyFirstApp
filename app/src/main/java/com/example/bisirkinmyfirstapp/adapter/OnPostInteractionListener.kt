package com.example.bisirkinmyfirstapp.adapter

import com.example.bisirkinmyfirstapp.dto.Post

interface OnPostInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onAvatarClick(post: Post) {}
    fun onPostClick(post: Post) {}
}
