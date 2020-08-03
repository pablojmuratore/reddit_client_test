package com.pablojmuratore.redditposts.screens.postslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pablojmuratore.redditposts.adapters.RedditPostsListAdapter
import com.pablojmuratore.redditposts.databinding.FragmentsPostsListBinding
import com.pablojmuratore.redditposts.model.RedditPost

class PostsListFragment : Fragment() {
    private lateinit var binding: FragmentsPostsListBinding
    lateinit var postsListAdapter: RedditPostsListAdapter
    private val viewModel: PostsListViewModel by lazy { ViewModelProvider(this).get(PostsListViewModel::class.java) }

    private lateinit var postsEventsListener: RedditPostsListAdapter.IRedditPostEventsListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentsPostsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initViews()
        initViewModel()
    }

    private fun initListeners() {
        postsEventsListener = object : RedditPostsListAdapter.IRedditPostEventsListener {
            override fun onRedditPostClicked(redditPost: RedditPost) {
                showRedditPost(redditPost)
            }
        }
    }

    private fun initViews() {
        binding.postsList.let {
            it.layoutManager = LinearLayoutManager(this.context)
            postsListAdapter = RedditPostsListAdapter(postsEventsListener)
            it.adapter = postsListAdapter
        }
    }

    private fun initViewModel() {
        viewModel.postsList.observe(viewLifecycleOwner, Observer {
            postsListAdapter.submitList(it)
        })

    }

    private fun showRedditPost(redditPost: RedditPost) {

    }
}