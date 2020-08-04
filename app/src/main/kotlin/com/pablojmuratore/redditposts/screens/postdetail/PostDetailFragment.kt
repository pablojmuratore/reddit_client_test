package com.pablojmuratore.redditposts.screens.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.pablojmuratore.redditposts.databinding.FragmentPostDetailBinding
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.util.GlideApp

class PostDetailFragment : Fragment() {
    private lateinit var binding: FragmentPostDetailBinding
    private val viewModel: PostDetailViewModel by lazy { ViewModelProvider(this).get(PostDetailViewModel::class.java) }
    private val args: PostDetailFragmentArgs? by navArgs()

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
        }
    }

    private fun initViewModels() {
        viewModel.redditPost.observe(viewLifecycleOwner, Observer {
            binding.author.text = it.author
            if (it.thumbnail != "self") {
                binding.image.visibility = View.VISIBLE
                GlideApp.with(this)
                    .load(it.thumbnail)
                    .into(binding.image)
            }
            else {
                binding.image.visibility = View.GONE
            }
            binding.title.text = it.title
        })
    }

}