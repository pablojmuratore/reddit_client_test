package com.pablojmuratore.redditposts.screens.postimage

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.pablojmuratore.redditposts.R
import com.pablojmuratore.redditposts.databinding.FragmentPostImageBinding
import com.pablojmuratore.redditposts.util.ImageDownloader
import com.pablojmuratore.redditposts.util.NetworkHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostImageFragment : Fragment() {
    private val PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 100

    private val args: PostImageFragmentArgs by navArgs()

    private lateinit var binding: FragmentPostImageBinding
    private var imageUrl: String? = null

    @Inject
    lateinit var networkHelper: NetworkHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPostImageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageUrl = args.imageUrl

        showImage(imageUrl)

        initEvents()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    private fun initEvents() {
        binding.saveImageButton.setOnClickListener {
            if (networkHelper.isNetworkAvailable()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    askPermissions()
                } else {
                    downloadImage(imageUrl)
                }
            } else {
                Snackbar.make(binding.root, getString(R.string.no_network_message), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun showImage(imageUrl: String?) {
        if (imageUrl != null) {
            if (networkHelper.isNetworkAvailable()) {
                Glide.with(this)
                    .load(imageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            binding.saveImageButton.visibility = View.GONE

                            Snackbar.make(binding.root, R.string.error_loading_image, Snackbar.LENGTH_SHORT).show()

                            Navigation.findNavController(binding.root).popBackStack()

                            return true
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            binding.saveImageButton.visibility = View.VISIBLE

                            return false
                        }

                    })
                    .into(binding.image)
            } else {
                Snackbar.make(binding.root, R.string.no_network_message, Snackbar.LENGTH_SHORT).show()

                Navigation.findNavController(binding.root).popBackStack()
            }

        } else {
            Snackbar.make(binding.root, R.string.error_loading_image, Snackbar.LENGTH_SHORT).show()

            Navigation.findNavController(binding.root).popBackStack()
        }
    }

    private fun downloadImage(imageUrl: String?) {
        binding.saveImageButton.visibility = View.GONE

        if (imageUrl != null) {
            Snackbar.make(binding.root, R.string.image_download_start_message, Snackbar.LENGTH_SHORT).show()

            val downloadManagerService = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val imageDownloader = ImageDownloader()
            imageDownloader.downloadImage(imageUrl, downloadManagerService)
        }
    }

    private fun askPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.permission_required))
                    .setMessage(getString(R.string.permission_required_save_photos))
                    .setPositiveButton(R.string.accept) { dialog, id ->
                        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE)
                    }
                    .setNegativeButton(R.string.deny) { dialog, id -> dialog.cancel() }
                    .show()
            } else {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE)
            }
        } else {
            downloadImage(imageUrl)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    downloadImage(imageUrl)
                } else {
                    Snackbar.make(binding.root, R.string.need_permission_to_download_images, Snackbar.LENGTH_SHORT).show()
                }

                return
            }
        }
    }

}