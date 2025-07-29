package com.pinterest.blankproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.pinterest.interview.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    lateinit var binding: MainFragmentBinding
    lateinit var adapter: GridAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    fun setupUI() {
        with(binding) {
            gridRecyclerView.apply {
                adapter = GridAdapter(viewLifecycleOwner.lifecycleScope).also { this@MainFragment.adapter = it }
                layoutManager = GridLayoutManager(requireContext(), GridAdapter.COLUMNS)
                addItemDecoration(DividerItemDecoration(requireContext(),
                    DividerItemDecoration.HORIZONTAL))
                addItemDecoration(DividerItemDecoration(requireContext(),
                    DividerItemDecoration.VERTICAL))
            }

            buttonReset.setOnClickListener {
                adapter.reset()
            }
        }
    }
}