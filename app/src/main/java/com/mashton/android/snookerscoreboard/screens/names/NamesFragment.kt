package com.mashton.android.snookerscoreboard.screens.names

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
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
            val action = NamesFragmentDirections.actionNamesToMatch()
            action.playerOneName = binding.playerOneNameEditText.text.toString()
            action.playerTwoName = binding.playerTwoNameEditText.text.toString()
            action.numberOfFrames = binding.numberOfFrames.text.toString().toInt()
            NavHostFragment.findNavController(this).navigate(action)
        }
        return binding.root
    }
}