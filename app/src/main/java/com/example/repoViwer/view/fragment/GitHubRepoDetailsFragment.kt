package com.example.repoViwer.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.repoViwer.R
import com.example.repoViwer.databinding.FragmentGitHubRepoDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GitHubRepoDetailsFragment : Fragment() {
    private lateinit var binding: FragmentGitHubRepoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentGitHubRepoDetailsBinding.inflate(lif, vg, false)
        return binding.root
    }

}