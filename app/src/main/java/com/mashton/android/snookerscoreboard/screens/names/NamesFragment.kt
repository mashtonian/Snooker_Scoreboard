package com.mashton.android.snookerscoreboard.screens.names

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.mashton.android.snookerscoreboard.R
import com.mashton.android.snookerscoreboard.databinding.NamesFragmentBinding
import kotlinx.android.synthetic.main.names_fragment.*

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
            val action = NamesFragmentDirections.actionNamesToMatch().apply {
                playerOneName = playerOneNameEditText.textOrDefault(R.string.PlayerOneName)
                playerTwoName = playerTwoNameEditText.textOrDefault(R.string.PlayerTwoName)
                numberOfFrames = binding.numberOfFrames.text.toString().toIntOrNull() ?: 1
            }
            NavHostFragment.findNavController(this).navigate(action)
        }
        return binding.root
    }

    private fun EditText.textOrDefault (defaultStringId: Int) :String {
        return when (this.text.toString()) {
            "" -> getString(defaultStringId)
            else -> this.text.toString()
        }
    }
}
