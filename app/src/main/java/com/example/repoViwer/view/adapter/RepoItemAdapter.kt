package com.example.repoViwer.view.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.repoViwer.R
import com.example.repoViwer.apiResponseModel.RepoResponseModel

class RepoItemAdapter(
    private val list: ArrayList<RepoResponseModel>, rFragment: Fragment, rContext: Activity
) : RecyclerView.Adapter<RepoItemAdapter.ViewHolder>() {

    private var listFiltered = list
    private var context = rContext
    private var fragment = rFragment



    interface RepoAdapterListener {
        fun onRepoClickListener(item: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cRepoModel = listFiltered[position]
        holder.tvRepoName.text = cRepoModel.items.get(position).name

    }

    override fun getItemCount(): Int {
        return if (listFiltered.isNotEmpty()) listFiltered.size else 0
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vRoot: LinearLayout = itemView.findViewById(R.id.vRoot)
        val ivPerson: ImageView = itemView.findViewById(R.id.iv_person_img)
        val tvDes: TextView = itemView.findViewById(R.id.tv_Description)
        val tvRepoName: TextView = itemView.findViewById(R.id.tv_projects_name)

    }
}