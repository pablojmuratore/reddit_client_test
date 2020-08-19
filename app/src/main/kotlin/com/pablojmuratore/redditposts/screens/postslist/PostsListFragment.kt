package com.pablojmuratore.redditposts.screens.postslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pablojmuratore.redditposts.CustomListAnimator
import com.pablojmuratore.redditposts.R
import com.pablojmuratore.redditposts.adapters.RedditPostsListAdapter
import com.pablojmuratore.redditposts.databinding.FragmentPostsListBinding
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostsListFragment : Fragment() {
    private lateinit var binding: FragmentPostsListBinding
    lateinit var postsListAdapter: RedditPostsListAdapter

    private val viewModel: PostsListViewModel by viewModels()

    private lateinit var postsEventsListener: RedditPostsListAdapter.IRedditPostEventsListener

    private var currentPost: RedditPost? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPostsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initViews()
        initViewModels()
        initEvents()
    }

    private fun initListeners() {
        postsEventsListener = object : RedditPostsListAdapter.IRedditPostEventsListener {
            override fun onRedditPostClicked(redditPost: RedditPost) {
                showRedditPost(redditPost)
            }

            override fun onRedditPostDismissed(redditPost: RedditPost) {
                dismissRedditPost(redditPost)
            }
        }
    }

    private fun initViews() {
        binding.postsList.let {
            it.layoutManager = LinearLayoutManager(this.context)
            postsListAdapter = RedditPostsListAdapter(postsEventsListener)
            it.adapter = postsListAdapter
            val dividerDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            dividerDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.list_item_divider)!!)
            it.addItemDecoration(dividerDecoration)
            it.itemAnimator = CustomListAnimator()
        }
    }

    private fun initViewModels() {
        viewModel.postsList.observe(viewLifecycleOwner, Observer {
            postsListAdapter.submitList(it)

            if (it.size > 0) {
                binding.dismissAllButton.visibility = View.VISIBLE
            } else {
                binding.dismissAllButton.visibility = View.GONE
            }
        })

        viewModel.refreshingPosts.observe(viewLifecycleOwner, Observer {
            if (!it) {
                binding.postsListSwipeToRefresh.isRefreshing = false
                binding.postsList.smoothScrollToPosition(0)
                binding.dismissAllButton.visibility = View.VISIBLE
            } else {
                binding.dismissAllButton.visibility = View.GONE
            }
        })

        viewModel.message.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                viewModel.clearMessage()
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        })

    }

    private fun initEvents() {
        binding.postsListSwipeToRefresh.setOnRefreshListener {
            viewModel.loadDataFromNetwork()
            showRedditPost(null)
        }

        binding.dismissAllButton.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.list_all_items_remove_animation)
            binding.postsList.startAnimation(animation).also {
                viewModel.dismissAllPosts()
            }

            showRedditPost(null)
        }
    }

    private fun showRedditPost(redditPost: RedditPost?) {
        currentPost = redditPost

        val arguments = Bundle()
        arguments.putParcelable("redditPost", redditPost)
        Navigation.findNavController(requireActivity(), R.id.post_detail_nav_host_fragment).navigate(R.id.postDetailFragment, arguments)

        if (redditPost != null) {
            (requireActivity() as MainActivity).closeDrawer()
        }
    }

    private fun dismissRedditPost(redditpost: RedditPost) {
        viewModel.dismissPost(redditpost)

        if (currentPost != null && currentPost!!.id == redditpost.id) {
            showRedditPost(null)
        }
    }

}