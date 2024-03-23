package com.example.repoViwer.view.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repoViwer.R
import com.example.repoViwer.apiResponseModel.RepoResponseModel
import com.example.repoViwer.databinding.GitHubRepoFragmentBinding
import com.example.repoViwer.view.adapter.RepoItemAdapter
import com.example.repoViwer.viewModel.RepoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GitHubRepoFragment : Fragment(), View.OnClickListener, RepoItemAdapter.RepoAdapterListener {
    private lateinit var binding: GitHubRepoFragmentBinding
    private lateinit var activity: Activity
    private lateinit var fragment: Fragment

    private var listRepo = ArrayList<RepoResponseModel>()
    private lateinit var adapter: RepoItemAdapter
    private lateinit var rvCommon: RecyclerView

    private val viewModel by viewModels<RepoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = requireActivity()
        fragment = this
        viewModel.application = activity.application

    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View? {
        binding = GitHubRepoFragmentBinding.inflate(lif, vg, false)
        initiateView()
        observeViewModel()
        setData()
        return binding.root
    }

    private fun initiateView() {

//        binding.animationView.setOnClickListener(this@ChatF)

    }

    private fun observeViewModel() {
        viewModel.repoResponseLiveData.observe(viewLifecycleOwner) {
            if (!it.incompleteResults) {
                viewModel.fetchRepositories()
            }

        }
    }

    private fun setData() {
        val lManager = LinearLayoutManager(activity)
        lManager.stackFromEnd = true
        rvCommon = binding.rvCommon
        rvCommon.layoutManager = lManager
        adapter = RepoItemAdapter(listRepo, fragment, activity)
        rvCommon.adapter = adapter
        adapter.notifyDataSetChanged()


    }

    override fun onClick(v: View?) {

    }

    override fun onRepoClickListener(item: View, position: Int) {
        TODO("Not yet implemented")
    }
}