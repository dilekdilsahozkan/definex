package com.example.definexcase.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.definexcase.BR
import com.example.definexcase.MainActivity
import com.example.definexcase.R
import com.example.definexcase.base.BaseAdapter
import com.example.definexcase.base.ViewState
import com.example.definexcase.data.dto.DiscoverDto
import com.example.definexcase.databinding.FragmentDiscoverBinding
import com.example.definexcase.databinding.ItemDiscoverBinding
import com.example.definexcase.databinding.ItemSecondHorizontalBinding
import com.example.definexcase.databinding.ItemVerticalBinding
import com.example.definexcase.presentation.viewmodel.DiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var viewModel: DiscoverViewModel

    private val token: String? by lazy {
        activity?.intent?.getStringExtra(LoginActivity.BUNDLE_TOKEN)
    }

    private val topAdapter : BaseAdapter<DiscoverDto, ItemDiscoverBinding> by lazy {
        BaseAdapter(R.layout.item_discover, BR.item)
    }
    private val middleAdapter : BaseAdapter<DiscoverDto, ItemSecondHorizontalBinding> by lazy {
        BaseAdapter(R.layout.item_second_horizontal, BR.item)
    }
    private val bottomAdapter : BaseAdapter<DiscoverDto, ItemVerticalBinding> by lazy {
        BaseAdapter(R.layout.item_vertical, BR.item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[DiscoverViewModel::class.java]

        val layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        binding.topRecyclerView.layoutManager = layoutManager
        binding.topRecyclerView.adapter = topAdapter

        val layoutManager2 = GridLayoutManager(activity, 1, RecyclerView.HORIZONTAL, false)
        binding.middleRecyclerView.layoutManager = layoutManager2
        binding.middleRecyclerView.adapter = middleAdapter

        val layoutManager3 = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        binding.bottomRecyclerView.layoutManager = layoutManager3
        binding.bottomRecyclerView.adapter = bottomAdapter

        addObservable()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        token?.let { viewModel.getHorizontal(it) }
        token?.let { viewModel.getSecondHorizontal(it) }
        token?.let { viewModel.getColumnList(it) }
    }

    private fun addObservable(){
        lifecycleScope.launch {
            viewModel.horizontalState.collect {
                when (it) {
                    is ViewState.Success -> {
                        topAdapter.submitList(it.data.list)
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.secondHorizontalState.collect {
                when (it) {
                    is ViewState.Success -> {
                        middleAdapter.submitList(it.data.list)
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.verticalState.collect {
                when (it) {
                    is ViewState.Success -> {
                        bottomAdapter.submitList(it.data.list)
                    }
                    else -> {}
                }
            }
        }
    }
}
