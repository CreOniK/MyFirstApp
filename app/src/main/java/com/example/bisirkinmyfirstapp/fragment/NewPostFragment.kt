package com.example.bisirkinmyfirstapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bisirkinmyfirstapp.R
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bisirkinmyfirstapp.adapter.OnPostInteractionListener
import com.example.bisirkinmyfirstapp.adapter.PostsAdapter
import com.example.bisirkinmyfirstapp.dto.Post
import com.example.bisirkinmyfirstapp.viewmodel.PostViewModel
import com.example.bisirkinmyfirstapp.activity.EditPostContract
import com.example.bisirkinmyfirstapp.databinding.FragmentFeedBinding
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bisirkinmyfirstapp.databinding.FragmentNewPostBinding
import com.example.bisirkinmyfirstapp.databinding.FragmentPostDetailBinding
import java.text.DecimalFormat


class NewPostFragment : Fragment() {

    private var _binding: FragmentNewPostBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPostBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id != 0L) {
                binding.edit.setText(post.content)
            } else {
                binding.edit.setText("")
            }
        }

        // Получаем текст для редактирования из аргументов
        val existingText = arguments?.getString("postContent")
        if (!existingText.isNullOrBlank()) {
            binding.edit.setText(existingText)
        }

        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isBlank()) {
                Toast.makeText(requireContext(), R.string.error_empty_content, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            viewModel.changeContent(text)
            viewModel.save()
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val text = binding.edit.text.toString()
            if (text.isNotBlank()) {
                viewModel.saveDraft(text)
            }
            findNavController().popBackStack()
        }

        viewModel.draft.observe(viewLifecycleOwner) { draftText ->
            if (draftText.isNotBlank()) {
                binding.edit.setText(draftText)
            }
        }






    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
