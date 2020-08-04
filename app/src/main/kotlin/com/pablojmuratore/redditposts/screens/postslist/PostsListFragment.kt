package com.pablojmuratore.redditposts.screens.postslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pablojmuratore.redditposts.R
import com.pablojmuratore.redditposts.adapters.RedditPostsListAdapter
import com.pablojmuratore.redditposts.databinding.FragmentPostsListBinding
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.screens.main.MainActivity
import com.pablojmuratore.redditposts.screens.postdetail.PostDetailFragmentDirections

class PostsListFragment : Fragment() {
    private lateinit var binding: FragmentPostsListBinding
    lateinit var postsListAdapter: RedditPostsListAdapter
    private val viewModel: PostsListViewModel by lazy { ViewModelProvider(requireActivity()).get(PostsListViewModel::class.java) }

    private lateinit var postsEventsListener: RedditPostsListAdapter.IRedditPostEventsListener

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
        }
    }

    private fun initViewModels() {
        viewModel.postsList.observe(viewLifecycleOwner, Observer {
            postsListAdapter.submitList(it)

            if (it.size > 0) {
                binding.emptyListMessage.visibility = View.GONE
                binding.postsList.visibility = View.VISIBLE
                binding.dismissAllButton.visibility = View.VISIBLE
            }
            else {
                binding.emptyListMessage.visibility = if (viewModel.refreshingPosts.value != true) View.VISIBLE else View.GONE
                binding.postsList.visibility = View.GONE
                binding.dismissAllButton.visibility = View.GONE
            }
        })

        viewModel.currentPost.observe(viewLifecycleOwner, Observer {
            Navigation.findNavController(requireActivity(), R.id.post_detail_nav_host_fragment).navigate(PostDetailFragmentDirections.actionPostDetailFragmentSelf(it))
        })

        viewModel.refreshingPosts.observe(viewLifecycleOwner, Observer {
            if (!it) {
                binding.postsListSwipeToRefresh.isRefreshing = false
                binding.postsList.smoothScrollToPosition(0)
//                viewModel.showPost(null)
                binding.dismissAllButton.visibility = View.VISIBLE
            }
            else {
                binding.dismissAllButton.visibility = View.GONE
            }
        })
    }

    private fun initEvents() {
        binding.postsListSwipeToRefresh.setOnRefreshListener {
            viewModel.loadDataFromNetwork()
        }

        binding.dismissAllButton.setOnClickListener {
            viewModel.dismissAllPosts()
        }
    }

    private fun showRedditPost(redditPost: RedditPost) {
        viewModel.showPost(redditPost)

        (requireActivity() as MainActivity).closeDrawer()
    }

    private fun dismissRedditPost(redditpost: RedditPost) {
        viewModel.dismissPost(redditpost)
    }

}