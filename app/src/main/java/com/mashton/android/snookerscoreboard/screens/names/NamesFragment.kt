package com.mashton.android.snookerscoreboard.screens.names

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mashton.android.snookerscoreboard.R
import com.mashton.android.snookerscoreboard.databinding.NamesFragmentBinding

/**
 * Fragment for the starting or title screen of the app
 */
class NamesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val binding: NamesFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.names_fragment, container, false)

        binding.startMatchButton.setOnClickListener {
            findNavController().navigate(NamesFragmentDirections.actionNamesToMatch())
        }
        return binding.root
    }
}