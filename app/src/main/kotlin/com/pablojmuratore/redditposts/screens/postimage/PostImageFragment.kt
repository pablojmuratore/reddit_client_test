package com.pablojmuratore.redditposts.screens.postimage

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pablojmuratore.redditposts.databinding.FragmentPostImageBinding
import com.pablojmuratore.redditposts.util.ImageDownloader

class PostImageFragment : Fragment() {
    private val PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 100

    private val args: PostImageFragmentArgs by navArgs()

    private lateinit var binding: FragmentPostImageBinding
    private var imageUrl: String? = null

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                askPermissions()
            } else {
                downloadImage(imageUrl)
            }
        }
    }

    private fun showImage(imageUrl: String?) {
        if (imageUrl != null) {
            Glide.with(this)
                .load(imageUrl)
                .into(binding.image)
        }
    }

    private fun downloadImage(imageUrl: String?) {
        if (imageUrl != null) {
            val downloadManagerService = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val imageDownloader = ImageDownloader()
            imageDownloader.downloadImage(imageUrl, downloadManagerService)
        }
    }

    private fun askPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Permission required")
                    .setMessage("Permission required to save photos from the Web.")
                    .setPositiveButton("Accept") { dialog, id ->
                        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE)
                    }
                    .setNegativeButton("Deny") { dialog, id -> dialog.cancel() }
                    .show()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE)
            }
        } else {
            downloadImage(imageUrl)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    downloadImage(imageUrl)
                } else {
                    // pjm no se puede bajar la imagen, mostrar algo
                }

                return
            }
        }
    }

}