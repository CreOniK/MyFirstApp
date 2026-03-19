package com.example.bisirkinmyfirstapp.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bisirkinmyfirstapp.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    // Исходные данные
    private var post = Post(
        id = 1,
        author = "Квантовая суперпозиция. Университет Квантовых взаимоотношений",
        content = "Квантовая суперпозиция — это состояние, при котором частица (например, электрон) может находиться в нескольких состояниях одновременно, пока на неё не воздействуют измерением. Другими словами, частица существует сразу во всех возможных вариантах, которые «схлопываются» в один определённый результат только при наблюдении.\n" +
                "Это явление — одно из ключевых отличий квантового мира от классического, где объект может находиться только в одном состоянии в данный момент времени.\n" +
                "Принцип:Суперпозиция представляет собой сумму (наложение) всех возможных состояний, в которых может находиться система. Математически состояние такой системы описывается волновой функцией, которая содержит в себе всю информацию о системе. ",
        published = "21 мая в 18:36",
        likedByMe = false,
        likes = 1300000,
        shares = 25,
        views = 5700
    )

    // MutableLiveData, который можно изменять
    private val _data = MutableLiveData(post)

    // Внешний доступ только для чтения (LiveData, а не MutableLiveData)
    override fun get(): LiveData<Post> = _data

    override fun like() {
        // Меняем состояние лайка на противоположное
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
        )
        // Оповещаем подписчиков об изменении
        _data.value = post
    }

    override fun share() {
        post = post.copy(
            shares = post.shares + 1
        )
        _data.value = post
    }

    override fun increaseViews() {
        // Можно будет реализовать позже
        post = post.copy(
            views = post.views + 1
        )
        _data.value = post
    }
}
