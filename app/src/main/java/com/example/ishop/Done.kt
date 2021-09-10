package com.example.ishop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.ishop.databinding.FragmentDoneBinding

class Done : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentDoneBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_done, container, false
        )

        return binding.root
    }
}