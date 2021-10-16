package com.supremehyo.locationsns.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.databinding.FragmentSampleBinding


class SampleFragment : Fragment() {

    private val binding by lazy { FragmentSampleBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val title = requireArguments().getString("title")


        return binding.root
    }

    companion object {
        fun newInstance(title: String) = SampleFragment().apply {
            arguments = Bundle().apply {
                putString("title", title)
            }
        }
    }
}