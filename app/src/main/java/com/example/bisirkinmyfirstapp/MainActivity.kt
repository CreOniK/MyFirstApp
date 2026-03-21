package com.example.bisirkinmyfirstapp
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.bisirkinmyfirstapp.adapter.OnPostInteractionListener
import com.example.bisirkinmyfirstapp.adapter.PostsAdapter
import com.example.bisirkinmyfirstapp.databinding.ActivityMainBinding
import com.example.bisirkinmyfirstapp.dto.Post
import com.example.bisirkinmyfirstapp.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()
    private var editingPostId: Long = 0L
    private val interactionListener = object : OnPostInteractionListener {
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }
        override fun onShare(post: Post) {
            viewModel.shareById(post.id)
            Toast.makeText(this@MainActivity, "Репост +1", Toast.LENGTH_SHORT).show()
        }

        override fun onEdit(post: Post) {
            editingPostId = post.id

            // Устанавливаем текст в поле ввода внутри TextInputLayout
            binding.textInputLayout.editText?.setText(post.content)
            binding.textInputLayout.editText?.setSelection(post.content.length)

            // Переводим фокус и показываем клавиатуру
            binding.textInputLayout.editText?.requestFocus()
            showKeyboard(binding.textInputLayout.editText ?: return)

            binding.cancelGroup.visibility = View.VISIBLE
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
            Toast.makeText(this@MainActivity, "Пост удален", Toast.LENGTH_SHORT).show()
        }

        override fun onAvatarClick(post: Post) {
            Toast.makeText(this@MainActivity, "Профиль: ${post.author}", Toast.LENGTH_SHORT).show()
            viewModel.increaseViews(post.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Настройка адаптера
        val adapter = PostsAdapter(interactionListener)
        binding.list.adapter = adapter

        // Наблюдение за списком постов
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        // Отслеживание изменений текста от пользователя
        binding.content.addTextChangedListener { text ->
            // Обновляем ViewModel при изменении текста пользователем
            viewModel.changeContent(text.toString())
        }

        // Кнопка сохранения
        binding.save.setOnClickListener {
            val text = binding.textInputLayout.editText?.text.toString()
            if (text.isBlank()) {
                Toast.makeText(this, "Введите текст поста", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (editingPostId != 0L) {
                viewModel.saveEditedPost(editingPostId, text)
                editingPostId = 0L
            } else {
                viewModel.changeContent(text)
                viewModel.save()
            }

            // Очищаем поле ввода и скрываем компоненты
            binding.textInputLayout.editText?.text?.clear()
            binding.cancelGroup.visibility = View.GONE


            // Скрываем клавиатуру. Важно передать поле ввода (EditText) внутрь контейнера.
            hideKeyboard(binding.textInputLayout.editText ?: return@setOnClickListener)
        }

        // Кнопка отмены редактирования
        binding.cancel.setOnClickListener {
            editingPostId = 0L
            binding.textInputLayout.editText?.text?.clear()
            binding.cancelGroup.visibility = View.GONE

            hideKeyboard(binding.textInputLayout.editText ?: return@setOnClickListener)

            viewModel.cancelEdit()
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.showSoftInput(view, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)
    }
}




