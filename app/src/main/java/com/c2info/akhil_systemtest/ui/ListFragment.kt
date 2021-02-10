package com.c2info.akhil_systemtest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.c2info.akhil_systemtest.MainActivity
import com.c2info.akhil_systemtest.adapters.UsersAdapter
import com.c2info.akhil_systemtest.databinding.FragmentListBinding
import com.c2info.akhil_systemtest.interfaces.CallBack
import com.c2info.akhil_systemtest.models.Data
import com.c2info.akhil_systemtest.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/** To list the images in grid view */
class ListFragment : Fragment() {

    var binding: FragmentListBinding? = null
    private var adapter = UsersAdapter(object : CallBack {
        override fun onClick(t: Any?, t2: Any?, t3: Any?) {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToProfileFragment(
                    t as Data
                )
            )
        }

    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentListBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = (requireActivity() as MainActivity).getViewModel()

        if (!viewModel.fetchedOnce) {
            viewModel.getData()
            viewModel.fetchedOnce = true
        }

        if (adapter.data.size == 0) {
            binding?.progress?.isVisible = true
            binding?.recyclerView?.layoutManager =
                LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            CoroutineScope(Dispatchers.Main).launch {
                binding?.progress?.isVisible = false
                binding?.recyclerView?.adapter = adapter
                AppDatabase.getAppDataBase(requireActivity())?.getUserDao()?.getAll()
                    ?.observe(viewLifecycleOwner, {
                        it?.let {
                            adapter.updateList(it as ArrayList<Data>) }
                    })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}