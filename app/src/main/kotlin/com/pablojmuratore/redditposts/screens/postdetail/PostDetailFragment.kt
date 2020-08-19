package com.pablojmuratore.redditposts.screens.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.pablojmuratore.redditposts.R
import com.pablojmuratore.redditposts.databinding.FragmentPostDetailBinding
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.util.GlideApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : Fragment() {
    private lateinit var binding: FragmentPostDetailBinding
    private val args: PostDetailFragmentArgs? by navArgs()
    private val viewModel: PostDetailViewModel by viewModels()
    private var redditPost: RedditPost? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPostDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        redditPost = args?.redditPost

        initViewModels()

        if (redditPost != null) {
            viewModel.markPostRead(redditPost!!.id)
            viewModel.loadRedditPost(redditPost!!.id)
        } else {
            viewModel.clearRedditPost()
        }

        initEvents()
    }

    private fun initViewModels() {
        viewModel.redditPost.observe(viewLifecycleOwner, Observer {
            showPostInfo(it)
        })
    }

    private fun showPostInfo(redditPost: RedditPost?) {
        if (redditPost != null) {
            binding.author.text = redditPost.author
            if (!redditPost.isTextPost) {
                binding.image.visibility = View.VISIBLE
                GlideApp.with(this)
                    .load(redditPost.thumbnail)
                    .into(binding.image)
            } else {
                binding.image.visibility = View.GONE
            }
            binding.title.text = redditPost.title
        } else {
            binding.author.text = ""
            binding.image.setImageDrawable(null)
            binding.title.text = ""
        }
    }

    private fun showImageFragment(imageUrl: String) {
        Navigation.findNavController(requireActivity(), R.id.post_detail_nav_host_fragment).navigate(PostDetailFragmentDirections.actionPostDetailFragmentToPostImageFragment(imageUrl))
    }

    private fun initEvents() {
        binding.image.setOnClickListener {
            val post = viewModel.redditPost.value

            if (post != null) {
                showImageFragment(post.imageUrl)
            }
        }
    }
}
