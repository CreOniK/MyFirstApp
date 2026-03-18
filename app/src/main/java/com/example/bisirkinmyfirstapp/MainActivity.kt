package com.example.bisirkinmyfirstapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.bisirkinmyfirstapp.databinding.ActivityMainBinding
import com.example.bisirkinmyfirstapp.dto.Post
import java.text.DecimalFormat

// Исправление: объявляем Post как data class
data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likes: Int,
    val shares: Int,
    val views: Int
)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Создаем экземпляр Post
        post = Post(
            id = 1,
            author = "Квантовая суперпозиция. Университет Квантовых взаимоотношений",
            content = "Квантовая суперпозиция — это состояние, при котором частица (например, электрон) может находиться в нескольких состояниях одновременно, пока на неё не воздействуют измерением. Другими словами, частица существует сразу во всех возможных вариантах, которые «схлопываются» в один определённый результат только при наблюдении.\n" +
                    "Это явление — одно из ключевых отличий квантового мира от классического, где объект может находиться только в одном состоянии в данный момент времени.\n" +
                    "Принцип:Суперпозиция представляет собой сумму (наложение) всех возможных состояний, в которых может находиться система. Математически состояние такой системы описывается волновой функцией, которая содержит в себе всю информацию о системе. ",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 999,
            shares = 25,
            views = 5700
        )

        // Отображаем данные на экране
        bindPost(post)

        // Настраиваем обработчики кликов
        setupClickListeners()
    }

    private fun bindPost(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            // Форматируем счётчики
            likeCount.text = formatCount(post.likes)
            shareCount.text = formatCount(post.shares)
            viewsCount.text = formatCount(post.views)

            // Ставим соответствующую иконку лайка
            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_like_filled)
            } else {
                like.setImageResource(R.drawable.ic_like_border)
            }

            // Заполняем ссылку (если есть)
            linkTitle.text = "Исследования кварков: 4 уровня мира"
            linkUrl.text = "Fisik.ru"
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            // Лайк
            like.setOnClickListener {
                // Изменяем состояние лайка
                post = post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )

                // Перерисовываем пост
                bindPost(post)

                // Подсказка
                Toast.makeText(this@MainActivity,
                    if (post.likedByMe) "Лайк поставлен" else "Лайк убран",
                    Toast.LENGTH_SHORT).show()
            }

            // Репост
            share.setOnClickListener {
                // Увеличиваем количество репостов
                post = post.copy(shares = post.shares + 1)

                // Обновляем интерфейс
                bindPost(post)

                Toast.makeText(this@MainActivity, "Репост +1", Toast.LENGTH_SHORT).show()
            }

            // Меню
            menu.setOnClickListener {
                Toast.makeText(this@MainActivity, "Меню поста", Toast.LENGTH_SHORT).show()
            }

            // Аватарка
            avatar.setOnClickListener {
                Toast.makeText(this@MainActivity, "Профиль автора", Toast.LENGTH_SHORT).show()
            }

            // Корневой элемент
            root.setOnClickListener {
                println("CLICK: корневой layout")
                Toast.makeText(this@MainActivity, "Клик по фону", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun formatCount(count: Int): String {
        return when {
            count >= 1_000_000 -> {
                val millions = count / 1_000_000.0
                if (millions % 1.0 == 0.0) {
                    "${millions.toInt()}M"
                } else {
                    DecimalFormat("#.#").format(millions) + "M"
                }
            }
            count >= 10_000 -> {
                "${count / 1000}K"
            }
            count >= 1_000 -> {
                val thousands = count / 1000.0
                if (thousands % 1.0 == 0.0) {
                    "${thousands.toInt()}K"
                } else {
                    DecimalFormat("#.#").format(thousands) + "K"
                }
            }
            else -> count.toString()
        }
    }
}