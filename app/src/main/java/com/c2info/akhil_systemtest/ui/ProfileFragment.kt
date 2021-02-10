package com.c2info.akhil_systemtest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.c2info.akhil_systemtest.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {


    private val args: ProfileFragmentArgs by navArgs()
    var binding: FragmentProfileBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentProfileBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            binding?.imageView?.let {
                Glide
                    .with(requireActivity())
                    .load(args.data?.avatar)
                    .override(800,800)
                    .into(it)
            }
            binding?.name?.text =
                String.format("%s", args.data?.first_name + " " + args.data?.last_name)

            binding?.email?.text =
                String.format("%s", args.data?.email)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}